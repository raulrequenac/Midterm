package com.ironhack.midterm.model;

import com.ironhack.midterm.exceptions.NotEnoughBalanceException;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class StudentChecking extends Checking {
    public StudentChecking() {
    }

    public StudentChecking(Money balance, Integer secretKey, AccountHolder primaryOwner) {
        super(balance, secretKey, primaryOwner);
    }

    @Override
    public void debit(Money amount) {
        if (this.balance.getAmount().compareTo(amount.getAmount())<0) throw new NotEnoughBalanceException();
        this.balance.decreaseAmount(amount);
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
    public String toString() {
        return "{" +
                "\n\tid: " + id +
                ",\n\tbalance: " + balance.toString() +
                ",\n\tsecretKey: " + secretKey +
                ",\n\tprimaryOwner: " + primaryOwner.getName() +
                ",\n\tpenaltyFee: " + penaltyFee +
                ",\n\tstatus: " + status +
                "\n}";
    }
}
