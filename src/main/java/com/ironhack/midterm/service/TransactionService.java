package com.ironhack.midterm.service;

import com.ironhack.midterm.exceptions.FraudDetectedException;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Transaction;
import com.ironhack.midterm.repository.CheckingRepository;
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

    public Transaction create(Checking account) {
        return transactionRepository.save(new Transaction(account));
    }

    public void isFraud(Checking account) {
        Integer today = transactionRepository.todayTotal(account.getId(), LocalDate.now().atStartOfDay().toLocalDate());
        Integer max = transactionRepository.maxTotal(account.getId());
        if (today>=max) {
            account.freeze();
            accountService.saveAccount(account);
            throw new FraudDetectedException();
        }
    }
}
