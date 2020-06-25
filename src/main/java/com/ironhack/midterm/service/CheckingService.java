package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.model.*;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.repository.StudentCheckingRepository;
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

    public Checking create(AccountInstance accountInstance, Integer secondaryOwnerId) {
        AccountHolder primaryOwner = accountHolderService.findById(accountInstance.getPrimaryOwnerId());
        AccountHolder secondaryOwner = null;
        if (secondaryOwnerId!=null)
            secondaryOwner = accountHolderService.findById(secondaryOwnerId);

        LocalDate today = LocalDate.now();
        if (today.minusYears(24).isBefore(primaryOwner.getDateOfBirth())) {
            StudentChecking studentChecking = new StudentChecking(accountInstance.getBalance(), accountInstance.getSecretKey(), primaryOwner);
            if (secondaryOwner != null) studentChecking.setSecondaryOwner(secondaryOwner);
            return studentCheckingRepository.save(studentChecking);
        }

        Checking checking = new Checking(accountInstance.getBalance(), accountInstance.getSecretKey(), primaryOwner);
        if (secondaryOwner != null) checking.setSecondaryOwner(secondaryOwner);
        return checkingRepository.save(checking);
    }
}
