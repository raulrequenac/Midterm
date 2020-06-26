package com.ironhack.midterm.controller.dto;

import java.math.BigDecimal;
import java.util.Currency;

public class Transference {
    private Integer receiverId;
    private String ownerName;
    private Currency currency;
    private BigDecimal amount;

    public Transference(Integer receiverId, String ownerName, Currency currency, BigDecimal amount) {
        this.receiverId = receiverId;
        this.ownerName = ownerName;
        this.currency = currency;
        this.amount = amount;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
