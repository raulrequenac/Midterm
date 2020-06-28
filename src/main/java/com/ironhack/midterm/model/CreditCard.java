package com.ironhack.midterm.model;

import com.ironhack.midterm.enums.AccountStatus;
import com.ironhack.midterm.exceptions.IllegalCreditLimitException;
import com.ironhack.midterm.exceptions.IllegalInterestRateException;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class CreditCard extends Savings {
    private BigDecimal creditLimit;

    public CreditCard() {
    }

    public CreditCard(Money balance, AccountHolder primaryOwner) {
        super(balance, null, primaryOwner);
        setMinimumBalance(new BigDecimal(0));
        this.creditLimit = new BigDecimal(100);
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        if (this.creditLimit.compareTo(BigDecimal.valueOf(100))<=0 ||
                this.creditLimit.compareTo(BigDecimal.valueOf(100000))>0)
            throw new IllegalCreditLimitException();
        this.creditLimit = creditLimit;
    }

    @Override
    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(BigDecimal.valueOf(0.1))<0 || interestRate.compareTo(BigDecimal.valueOf(0.2))>=0)
            throw new IllegalInterestRateException(0.1, 0.2);
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
    public String getSecretKey() {
        return super.getSecretKey();
    }

    @Override
    @Transient
    public void setSecretKey(Integer secretKey) {

    }

    @Override
    @Transient
    public BigDecimal getMinimumBalance() {
        return super.getMonthlyMaintenanceFee();
    }

    @Override
    @Transient
    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
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

    @Override
    public String toString() {
        return "{" +
                "\n\tid: " + id +
                ",\n\tbalance: " + balance +
                ",\n\tprimaryOwner: " + primaryOwner.getName() +
                ",\n\tpenaltyFee: " + penaltyFee +
                ",\n\tinterestRate: " + interestRate +
                ",\n\tcreditLimit: " + creditLimit +
                "\n}";
    }
}
