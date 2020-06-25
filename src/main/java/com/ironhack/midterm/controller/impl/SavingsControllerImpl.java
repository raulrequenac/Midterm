package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.controller.interfaces.SavingsController;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.Savings;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.service.SavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class SavingsControllerImpl implements SavingsController {
    @Autowired
    private SavingsService savingsService;

    @PostMapping("/accounts/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings create(@RequestBody AccountInstance accountInstance,
                           @RequestParam(value = "interestRate", required = false) BigDecimal interestRate,
                           @RequestParam(value = "minimumBalance", required = false) BigDecimal minimumBalance,
                           @RequestParam(value = "secondaryOwnerId", required = false) Integer secondaryOwnerId) {
        return savingsService.create(accountInstance, interestRate, minimumBalance, secondaryOwnerId);
    }
}
