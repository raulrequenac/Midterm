package com.ironhack.midterm.controller.dto;

import com.ironhack.midterm.model.Money;

import java.math.BigDecimal;
import java.util.Currency;

public class CreditCardInstance {
    private BigDecimal amount;
    private Currency currency;
    private Integer primaryOwnerId;

    public CreditCardInstance(BigDecimal amount, Currency currency, Integer primaryOwnerId) {
        this.amount = amount;
        this.currency = currency;
        this.primaryOwnerId = primaryOwnerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Integer getPrimaryOwnerId() {
        return primaryOwnerId;
    }
}
