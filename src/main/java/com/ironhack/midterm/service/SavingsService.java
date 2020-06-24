package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.exceptions.ForbiddenAccessException;
import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.exceptions.NotLoggedInException;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.Savings;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.repository.SavingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SavingsService {
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private AccountHolderService accountHolderService;

    public Savings findById(User user, Integer id) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        Savings savings =  savingsRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
        if (!user.canAccess(savings)) throw new ForbiddenAccessException();
        return savings;

    }

    public Money findBalance(User user, Integer id) {
        return findById(user, id).getBalance();
    }

    public Savings create(AccountInstance accountInstance, BigDecimal interestRate, BigDecimal minimumBalance, Integer secondaryOwnerId) {
        AccountHolder owner = accountHolderService.findById(accountInstance.getPrimaryOwnerId());
        Savings savings = new Savings(accountInstance.getBalance(), accountInstance.getSecretKey(), owner);
        if (secondaryOwnerId!=null) savings.setSecondaryOwner(accountHolderService.findById(secondaryOwnerId));
        if (interestRate!=null) savings.setInterestRate(interestRate);
        if (minimumBalance!=null) savings.setMinimumBalance(minimumBalance);
        return savings;
    }

    public void credit(User user, Integer id, Money amount) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        Savings savings = findById(user, id);
        if (!user.canAccess(savings)) throw new ForbiddenAccessException();
        savings.credit(amount);
        savingsRepository.save(savings);
    }

    public void debit (User user, Integer id, Money amount) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        Savings savings = findById(user, id);
        if (!user.canAccess(savings)) throw new ForbiddenAccessException();
        savings.debit(amount);
        savingsRepository.save(savings);
    }
}
