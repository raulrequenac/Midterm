package com.ironhack.midterm.model;

import com.ironhack.midterm.exceptions.IllegalCreditLimitException;
import com.ironhack.midterm.exceptions.IllegalInterestRateException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class CreditCard extends Savings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Money creditLimit;

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, null, primaryOwner, secondaryOwner, null);
        super.setMonthlyMaintenanceFee(new Money(new BigDecimal(0)));
        super.setMinimumBalance(new Money(new BigDecimal(0)));
        this.creditLimit = new Money(new BigDecimal(100));
    }

    public CreditCard(Money balance, AccountHolder primaryOwner) {
        this(balance, primaryOwner, null);
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
}
