package com.ironhack.midterm.model;

import com.ironhack.midterm.enums.AccountStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Checking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Money balance;
    private Integer secretKey;
    private AccountHolder primaryOwner;
    private AccountHolder secondaryOwner;
    protected Money minimumBalance;
    private Money penaltyFee;
    private Money monthlyMaintenanceFee;
    private AccountStatus status;

    public Checking(Money balance, Integer secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, AccountStatus status) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.minimumBalance = new Money(new BigDecimal(250));
        this.penaltyFee = new Money(new BigDecimal(40));
        this.monthlyMaintenanceFee = new Money(new BigDecimal(12));
        this.status = status;
    }

    public Checking(Money balance, Integer secretKey, AccountHolder primaryOwner, AccountStatus status) {
        this(balance, secretKey, primaryOwner, null, status);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Integer getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Integer secretKey) {
        this.secretKey = secretKey;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Money penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public Money getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(Money monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
