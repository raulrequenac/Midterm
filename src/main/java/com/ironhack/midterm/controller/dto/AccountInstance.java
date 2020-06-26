package com.ironhack.midterm.controller.dto;

import com.ironhack.midterm.enums.AccountStatus;
import com.ironhack.midterm.model.Money;

public class AccountInstance extends CreditCardInstance {
    private Integer secretKey;

    public AccountInstance(Money balance, Integer primaryOwnerId, Integer secretKey) {
        super(balance, primaryOwnerId);
        this.secretKey = secretKey;
    }

    public Integer getSecretKey() {
        return secretKey;
    }
}
