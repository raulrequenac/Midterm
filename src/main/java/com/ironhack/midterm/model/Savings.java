package com.ironhack.midterm.model;

import com.ironhack.midterm.enums.AccountStatus;
import com.ironhack.midterm.exceptions.IllegalInterestRateException;
import com.ironhack.midterm.exceptions.IllegalMinimumBalanceException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Savings extends Checking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    protected BigDecimal interestRate;

    public Savings(Money balance, Integer secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, AccountStatus status) {
        super(balance, secretKey, primaryOwner, secondaryOwner, status);
        super.setMonthlyMaintenanceFee(new Money(new BigDecimal(0)));
        super.setMinimumBalance(new Money(new BigDecimal(1000)));
        this.interestRate = new BigDecimal(0.0025);
    }

    public Savings(Money balance, Integer secretKey, AccountHolder primaryOwner, AccountStatus status) {
        this(balance, secretKey, primaryOwner, null, status);
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
    public void setMinimumBalance(Money minimumBalance) {
        if (minimumBalance.getAmount().compareTo(BigDecimal.valueOf(100))<0 ||
                minimumBalance.getAmount().compareTo(BigDecimal.valueOf(1000))>=0)
            throw new IllegalMinimumBalanceException();
        this.minimumBalance = minimumBalance;
    }
}
