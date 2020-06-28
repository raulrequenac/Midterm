package com.ironhack.midterm.service;

import com.ironhack.midterm.MidtermApplication;
import com.ironhack.midterm.exceptions.FraudDetectedException;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Transaction;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDetailsServiceImpl userService;

    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public Transaction findLastTransaction(Integer id) {
        return transactionRepository.findLastTransaction(id);
    }

    public Transaction create(User user, Checking account) {
        LOGGER.info("[INIT] - Create Transaction Account");
        Transaction t = transactionRepository.save(new Transaction(account, user));
        LOGGER.info("[END] - Create Transaction Account");
        return t;
    }

    public void isFraud(User u, Checking account) {
        LOGGER.info("[INIT] - Is Account with id: "+account.getId()+" fraud");
        User user = userService.findById(u.getId());
        Transaction lastTransaction = findLastTransaction(account.getId());
        long secsBetween = lastTransaction==null ? 2 : Duration.between(lastTransaction.getRealizedAt(), LocalDateTime.now()).toSeconds();
        if (secsBetween<=1) freeze(account);
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        Integer today = transactionRepository.todayTotal(account.getId(), user.getId(), startOfDay);
        Integer defaultMax = transactionRepository.maxTotal(account.getId(), user.getId(), startOfDay);
        double max = defaultMax==null ? 3 : (Math.max(defaultMax, 2)*1.5);
        if (today>=max) freeze(account);
        LOGGER.info("[END] - Is Account with id: "+account.getId()+" fraud");
    }

    private void freeze(Checking account) {
        account.freeze();
        accountService.saveAccount(account);
        throw new FraudDetectedException();
    }
}
