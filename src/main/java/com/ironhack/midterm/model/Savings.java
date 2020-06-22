package com.ironhack.midterm.model;

import com.ironhack.midterm.exceptions.IllegalInterestRateException;
import com.ironhack.midterm.exceptions.IllegalMinimumBalanceException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Savings extends Checking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    protected BigDecimal interestRate;
    protected LocalDate interestDate;

    public Savings() {
    }

    public Savings(Money balance, Integer secretKey, AccountHolder primaryOwner) {
        super(balance, secretKey, primaryOwner);
        super.setMinimumBalance(new BigDecimal(1000));
        this.interestRate = new BigDecimal(0.0025);
        this.interestDate = LocalDate.now();
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(BigDecimal.valueOf(0))<0 || interestRate.compareTo(BigDecimal.valueOf(0.5))>0)
            throw new IllegalInterestRateException();
        this.interestRate = interestRate;
    }

    @Override
    public void setMinimumBalance(BigDecimal minimumBalance) {
        if (minimumBalance.compareTo(BigDecimal.valueOf(100))<0 ||
                minimumBalance.compareTo(BigDecimal.valueOf(1000))>=0)
            throw new IllegalMinimumBalanceException();
        this.minimumBalance = minimumBalance;
    }

    @Override
    public Money getBalance() {
        LocalDate today = LocalDate.now();
        for (long years = ChronoUnit.YEARS.between(today, this.interestDate); years>0; years--)
            this.credit(new Money(this.balance.getAmount().multiply(this.interestRate)));
        this.interestDate = LocalDate.of(today.getYear(), today.getMonth(), this.interestDate.getDayOfMonth());
        return this.balance;
    }

    @Override
    @Transient
    public BigDecimal getMonthlyMaintenanceFee() {
        return super.getMonthlyMaintenanceFee();
    }

    @Override
    @Transient
    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        super.setMonthlyMaintenanceFee(monthlyMaintenanceFee);
    }
}
