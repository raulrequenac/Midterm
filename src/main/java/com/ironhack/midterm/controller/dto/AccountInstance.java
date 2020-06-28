package com.ironhack.midterm.controller.dto;

import com.ironhack.midterm.enums.AccountStatus;
import com.ironhack.midterm.model.Money;

import java.math.BigDecimal;
import java.util.Currency;

public class AccountInstance extends CreditCardInstance {
    private Integer secretKey;

    public AccountInstance(BigDecimal amount, Currency currency, Integer primaryOwnerId, Integer secretKey) {
        super(amount, currency, primaryOwnerId);
        this.secretKey = secretKey;
    }

    public Integer getSecretKey() {
        return secretKey;
    }
}
