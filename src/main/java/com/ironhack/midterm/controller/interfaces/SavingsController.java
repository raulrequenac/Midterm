package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.Savings;
import com.ironhack.midterm.model.User;

import java.math.BigDecimal;

public interface SavingsController {
    Savings create(AccountInstance accountInstance, BigDecimal interestRate, BigDecimal minimumBalance, Integer secondaryOwnerId);
}
