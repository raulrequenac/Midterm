package com.ironhack.midterm.service;

import com.ironhack.midterm.exceptions.FraudDetectedException;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Transaction;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;

    public Transaction create(User user, Checking account) {
        return transactionRepository.save(new Transaction(account, user));
    }

    public void isFraud(User user, Checking account) {
        LocalDate startOfDay = LocalDate.now().atStartOfDay().toLocalDate();
        Integer today = transactionRepository.todayTotal(account.getId(), user.getId(), startOfDay);
        Integer defaultMax = transactionRepository.maxTotal(account.getId(), user.getId(), startOfDay);
        double max = defaultMax==null ? 3 : (Math.max(defaultMax, 2)*1.5);
        if (today>=max) {
            account.freeze();
            accountService.saveAccount(account);
            throw new FraudDetectedException();
        }
    }
}
