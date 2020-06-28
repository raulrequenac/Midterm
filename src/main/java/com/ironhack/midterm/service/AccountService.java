package com.ironhack.midterm.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ironhack.midterm.MidtermApplication;
import com.ironhack.midterm.controller.dto.Transference;
import com.ironhack.midterm.exceptions.*;
import com.ironhack.midterm.model.*;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.repository.CreditCardRepository;
import com.ironhack.midterm.repository.SavingsRepository;
import com.ironhack.midterm.repository.StudentCheckingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AccountService {
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private AccountHolderService accountHolderService;
    @Autowired
    private ThirdPartyService thirdPartyService;
    @Autowired
    private CheckingService checkingService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserDetailsServiceImpl userService;

    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public String findByIdAndReturnString(User u, Integer id) {
        return findById(u, id).toString() ;
    }

    public Checking findById(User u, Integer id) {
        LOGGER.info("[INIT] - Find account with id: "+id);
        User user = userService.findById(u.getId());
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        Checking checking = findById(id);
        if (!canAccess(id, user)) throw new ForbiddenAccessException();
        LOGGER.info("[END] - Find account with id: "+id);
        return checking;
    }

    public Checking findById(Integer id) {
        LOGGER.info("[INIT] - Find account with id: "+id);
        Checking account = checkingRepository.findById(id).orElseThrow(() -> new IdNotFoundException("account", id));
        LOGGER.info("[END] - Find account with id: "+id);
        return account;
    }

    public Checking findByIdAndOwnerName(String ownerName, Integer id) {
        LOGGER.info("[INIT] - Find account with id: "+id+" and owner name: "+ownerName);
        Checking account = findById(id);
        if (!account.getPrimaryOwner().getName().equals(ownerName) &&
                (account.getSecondaryOwner() == null || !account.getSecondaryOwner().getName().equals(ownerName)))
            throw new NameNotFoundException();
        LOGGER.info("[END] - Find account with id: "+id+" and owner name: "+ownerName);
        return account;
    }

    public Money findBalance(User u, Integer id) {
        LOGGER.info("[INIT] - Find account balance");
        User user = userService.findById(u.getId());
        Money balance = findById(user, id).getBalance();
        LOGGER.info("[END] - Find account balance");
        return balance;
    }

    public void credit(User u, Integer id, Money amount, String hashedKey) {
        LOGGER.info("[INIT] - Credit account with id: "+id+" from User: "+u.getId());
        User user = userService.findById(u.getId());
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        Checking account = findById(user, id);
        if (account.isFrozen()) throw new FrozenAccountException();
        if (!canAccess(id, user) && !thirdPartyService.correctHashedKey(user, hashedKey)) throw new ForbiddenAccessException();
        transactionService.isFraud(user, account);
        account.credit(amount);
        saveAccount(account);
        transactionService.create(user, account);
        LOGGER.info("[END] - Credit account with id: "+id+" from User: "+user.getId());
    }

    public void debit (User u, Integer id, Money amount, String hashedKey) {
        LOGGER.info("[INIT] - Debit account with id: "+id+" from User: "+u.getId());
        User user = userService.findById(u.getId());
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        Checking account = findById(user, id);
        if (account.isFrozen()) throw new FrozenAccountException();
        if (!canAccess(id, user) && !thirdPartyService.correctHashedKey(user, hashedKey)) throw new ForbiddenAccessException();
        transactionService.isFraud(user, account);
        account.debit(amount);
        saveAccount(account);
        transactionService.create(user, account);
        LOGGER.info("[END] - Debit account with id: "+id+" from User: "+user.getId());
    }

    @Transactional(dontRollbackOn = {FraudDetectedException.class})
    public void transfer(User u, Integer accSenderId, Transference transference) {
        LOGGER.info("[INIT] - Transfer from account with id: "+accSenderId+" to account with id: "+transference.getReceiverId());
        User user = userService.findById(u.getId());
        Checking accSender = findById(user, accSenderId);
        Checking accReceiver = findByIdAndOwnerName(transference.getOwnerName(), transference.getReceiverId());

        Money amount = new Money(transference.getAmount(), transference.getCurrency());
        debit(user, accSenderId, amount, null);
        saveAccount(accSender);
        credit(user, accReceiver.getId(), amount, null);
        saveAccount(accReceiver);
        LOGGER.info("[END] - Transfer from account with id: "+accSenderId+" to account with id: "+transference.getReceiverId());
    }

    public void unfreeze(User u, Integer id) {
        LOGGER.info("[INIT] - Unfreeze account with id: "+id);
        User user = userService.findById(u.getId());
        Checking account = findById(id);
        account.unFreeze();
        saveAccount(account);
        LOGGER.info("[END] - Unfreeze account with id: "+id);
    }

    public boolean canAccess(Integer accountId, User user) {
        LOGGER.info("[INIT] - Can user: "+user.getName()+" access account with id: "+accountId);
        boolean canAccess = user instanceof Admin || checkingRepository.findByIdAndPrimaryOwner(accountId, user)!=null ||
                checkingRepository.findByIdAndSecondaryOwner(accountId, user)!=null;
        LOGGER.info("[END] - Can user: "+user.getName()+" access account with id: "+accountId);
        return canAccess;
    }

    public void saveAccount(Checking account) {
        LOGGER.info("[INIT] - Save account with id: "+account.getId());
        if (account instanceof CreditCard) creditCardRepository.save((CreditCard)account);
        if (account instanceof Savings) savingsRepository.save((Savings)account);
        if (account instanceof StudentChecking) studentCheckingRepository.save((StudentChecking) account);
        else checkingRepository.save(account);
        LOGGER.info("[END] - Save account with id: "+account.getId());
    }
}
