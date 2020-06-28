package com.ironhack.midterm.service;

import com.ironhack.midterm.MidtermApplication;
import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.model.*;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.repository.StudentCheckingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CheckingService {
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private AccountHolderService accountHolderService;

    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public Checking create(AccountInstance accountInstance, Integer secondaryOwnerId) {
        LOGGER.info("[INIT] - Create Checking Account");
        AccountHolder primaryOwner = accountHolderService.findById(accountInstance.getPrimaryOwnerId());
        AccountHolder secondaryOwner = null;
        if (secondaryOwnerId!=null)
            secondaryOwner = accountHolderService.findById(secondaryOwnerId);
        Money balance = new Money(accountInstance.getAmount(), accountInstance.getCurrency());

        LocalDate today = LocalDate.now();
        if (today.minusYears(24).isBefore(primaryOwner.getDateOfBirth())) {
            LOGGER.info("[END] - Create Checking Account: Primary Owner is under 24yo");
            LOGGER.info("[INIT] - Create Student Checking Account");
            StudentChecking studentChecking = new StudentChecking(balance, accountInstance.getSecretKey(), primaryOwner);
            if (secondaryOwner != null) studentChecking.setSecondaryOwner(secondaryOwner);
            StudentChecking st = studentCheckingRepository.save(studentChecking);
            LOGGER.info("[END] - Create Student Checking Account");
            return st;
        }

        Checking checking = new Checking(balance, accountInstance.getSecretKey(), primaryOwner);
        if (secondaryOwner != null) checking.setSecondaryOwner(secondaryOwner);
        Checking c = checkingRepository.save(checking);
        LOGGER.info("[END] - Create Checking Account");
        return c;
    }
}
