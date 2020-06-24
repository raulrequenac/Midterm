package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.controller.dto.CreditCardInstance;
import com.ironhack.midterm.model.CreditCard;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.User;

import java.math.BigDecimal;

public interface CreditCardController {
    CreditCard findById(User user, Integer id);
    Money findBalance(User user, Integer id);
    CreditCard create(CreditCardInstance creditCardInstance, BigDecimal interestRate, BigDecimal creditLimit, Integer secondaryOwnerId);
    void credit(User user, Integer id, Money amount);
    void debit(User user, Integer id, Money amount);
}
