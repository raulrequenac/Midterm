package com.ironhack.midterm.model;

import com.ironhack.midterm.enums.AccountStatus;
import com.ironhack.midterm.exceptions.IllegalCreditLimitException;
import com.ironhack.midterm.exceptions.IllegalInterestRateException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class CreditCard extends Savings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Money creditLimit;

    public CreditCard() {
    }

    public CreditCard(Money balance, AccountHolder primaryOwner) {
        super(balance, null, primaryOwner);
        super.setMinimumBalance(new BigDecimal(0));
        this.creditLimit = new Money(new BigDecimal(100));
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Money creditLimit) {
        if (this.creditLimit.getAmount().compareTo(BigDecimal.valueOf(100))<=0 ||
                this.creditLimit.getAmount().compareTo(BigDecimal.valueOf(100000))>0)
            throw new IllegalCreditLimitException();
        this.creditLimit = creditLimit;
    }

    @Override
    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(BigDecimal.valueOf(0.1))<0 || interestRate.compareTo(BigDecimal.valueOf(0.2))>=0)
            throw new IllegalInterestRateException();
        this.interestRate = interestRate;
    }

    @Override
    public Money getBalance() {
        LocalDate today = LocalDate.now();
        for (long monthsDiff = ChronoUnit.MONTHS.between(this.interestDate, today); monthsDiff>0; monthsDiff--)
            this.credit(new Money(this.balance.getAmount().multiply(this.interestRate)));
        this.interestDate = LocalDate.of(today.getYear(), today.getMonth(), this.interestDate.getDayOfMonth());
        return this.balance;
    }

    @Override
    @Transient
    public Integer getSecretKey() {
        return super.getSecretKey();
    }

    @Override
    @Transient
    public void setSecretKey(Integer secretKey) {
        super.setSecretKey(secretKey);
    }

    @Override
    @Transient
    public BigDecimal getMinimumBalance() {
        return super.getMonthlyMaintenanceFee();
    }

    @Override
    @Transient
    public void setMinimumBalance(BigDecimal minimumBalance) {
        super.setMonthlyMaintenanceFee(minimumBalance);
    }

    @Override
    @Transient
    public AccountStatus getStatus() {
        return super.getStatus();
    }

    @Override
    @Transient
    public void setStatus(AccountStatus status) {
        super.setStatus(status);
    }
}
