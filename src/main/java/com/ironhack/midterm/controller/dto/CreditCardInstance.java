package com.ironhack.midterm.controller.dto;

import com.ironhack.midterm.model.Money;

public class CreditCardInstance {
    private Money balance;
    private Integer primaryOwnerId;

    public CreditCardInstance(Money balance, Integer primaryOwnerId) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
    }

    public Money getBalance() {
        return balance;
    }

    public Integer getPrimaryOwnerId() {
        return primaryOwnerId;
    }
}
