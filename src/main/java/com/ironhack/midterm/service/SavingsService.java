package com.ironhack.midterm.service;

import com.ironhack.midterm.MidtermApplication;
import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.exceptions.ForbiddenAccessException;
import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.exceptions.NotLoggedInException;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.Savings;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.repository.SavingsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SavingsService {
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private AccountHolderService accountHolderService;

    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public Savings create(AccountInstance accountInstance, BigDecimal interestRate, BigDecimal minimumBalance, Integer secondaryOwnerId) {
        LOGGER.info("[INIT] - Create Savings Account");
        AccountHolder owner = accountHolderService.findById(accountInstance.getPrimaryOwnerId());
        Money balance = new Money(accountInstance.getAmount(), accountInstance.getCurrency());
        Savings savings = new Savings(balance, accountInstance.getSecretKey(), owner);
        if (secondaryOwnerId!=null) savings.setSecondaryOwner(accountHolderService.findById(secondaryOwnerId));
        if (interestRate!=null) savings.setInterestRate(interestRate);
        if (minimumBalance!=null) savings.setMinimumBalance(minimumBalance);
        Savings s = savingsRepository.save(savings);
        LOGGER.info("[END] - Create Savings Account");
        return s;
    }
}
