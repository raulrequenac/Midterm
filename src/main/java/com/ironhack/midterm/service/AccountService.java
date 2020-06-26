package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.Transference;
import com.ironhack.midterm.exceptions.ForbiddenAccessException;
import com.ironhack.midterm.exceptions.FrozenAccountException;
import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.exceptions.NotLoggedInException;
import com.ironhack.midterm.model.*;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.repository.CreditCardRepository;
import com.ironhack.midterm.repository.SavingsRepository;
import com.ironhack.midterm.repository.StudentCheckingRepository;
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
    private CheckingService checkingService;
    @Autowired
    private TransactionService transactionService;

    public Checking findById(User user, Integer id) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        Checking checking = checkingRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
        if (!canAccess(id, user)) throw new ForbiddenAccessException();
        return checking;
    }

    public Money findBalance(User user, Integer id) {
        return findById(user, id).getBalance();
    }

    public void credit(User user, Integer id, Money amount) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        Checking account = findById(user, id);
        if (account.isFrozen()) throw new FrozenAccountException();
        if (!canAccess(id, user)) throw new ForbiddenAccessException();
        transactionService.isFraud(user, account);
        account.credit(amount);
        saveAccount(account);
        transactionService.create(user, account);
    }

    public void debit (User user, Integer id, Money amount) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        Checking account = findById(user, id);
        if (account.isFrozen()) throw new FrozenAccountException();
        if (!canAccess(id, user)) throw new ForbiddenAccessException();
        transactionService.isFraud(user, account);
        account.debit(amount);
        saveAccount(account);
        transactionService.create(user, account);
    }

    @Transactional
    public void transfer(User user, Integer accSenderId, Transference transference) {
        Checking accSender = findById(user, accSenderId);
        AccountHolder receiver = accountHolderService.findByName(transference.getOwnerName());
        Checking accReceiver = findById(receiver, transference.getReceiverId());

        Money amount = new Money(transference.getAmount(), transference.getCurrency());
        accSender.debit(amount);
        saveAccount(accSender);
        accReceiver.credit(amount);
        saveAccount(accReceiver);
    }

    public void unfreeze(User user, Integer id) {
        Checking account = findById(user, id);
        account.unFreeze();
        saveAccount(account);
    }

    public boolean canAccess(Integer accountId, User user) {
        if (user.getRoles().contains("ADMIN")) return true;
        return checkingRepository.findByIdAndPrimaryOwner(accountId, user)!=null ||
                checkingRepository.findByIdAndSecondaryOwner(accountId, user)!=null;
    }

    public void saveAccount(Checking account) {
        if (account instanceof CreditCard) creditCardRepository.save((CreditCard)account);
        if (account instanceof Savings) savingsRepository.save((Savings)account);
        if (account instanceof StudentChecking) studentCheckingRepository.save((StudentChecking) account);
        else checkingRepository.save(account);
    }
}
