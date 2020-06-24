package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.Savings;
import com.ironhack.midterm.model.User;

import java.math.BigDecimal;

public interface SavingsController {
    Savings findById(User user, Integer id);
    Money findBalance(User user, Integer id);
    Savings create(AccountInstance accountInstance, BigDecimal interestRate, BigDecimal minimumBalance, Integer secondaryOwnerId);
    void credit(User user, Integer id, Money amount);
    void debit(User user, Integer id, Money amount);
}
