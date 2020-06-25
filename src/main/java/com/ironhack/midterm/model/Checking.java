package com.ironhack.midterm.model;

import com.ironhack.midterm.enums.AccountStatus;
import com.ironhack.midterm.exceptions.NotEnoughBalanceException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Checking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    protected Money balance;
    private Integer secretKey;
    @ManyToOne
    private AccountHolder primaryOwner;
    @ManyToOne
    private AccountHolder secondaryOwner;
    protected BigDecimal minimumBalance;
    private BigDecimal penaltyFee;
    private BigDecimal monthlyMaintenanceFee;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    public Checking() {
    }

    public Checking(Money balance, Integer secretKey, AccountHolder primaryOwner) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = null;
        this.minimumBalance = new BigDecimal(250);
        this.penaltyFee = new BigDecimal(40);
        this.monthlyMaintenanceFee = new BigDecimal(12);
        this.status = AccountStatus.ACTIVE;
    }

    public void credit(Money amount) {
        this.balance.increaseAmount(amount);
    }

    public void debit(Money amount) {
        if (this.balance.getAmount().compareTo(amount.getAmount())<0) throw new NotEnoughBalanceException();

        BigDecimal newBalance = this.balance.getAmount().subtract(amount.getAmount());
        if (this.balance.getAmount().compareTo(this.minimumBalance)>=0 && newBalance.compareTo(this.minimumBalance)<0)
            this.balance.decreaseAmount(this.penaltyFee);

        this.balance.decreaseAmount(amount);
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

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public void freeze() {
        this.status = AccountStatus.FROZEN;
    }

    public void unFreeze() {
        this.status = AccountStatus.ACTIVE;
    }

    public boolean isFrozen() { return this.status.equals(AccountStatus.FROZEN); }
}
