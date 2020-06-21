package com.ironhack.midterm.model;

import com.ironhack.midterm.enums.AccountStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class StudentChecking extends Checking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public StudentChecking(Money balance, Integer secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, AccountStatus status) {
        super(balance, secretKey, primaryOwner, secondaryOwner, status);
        super.setMonthlyMaintenanceFee(new Money(new BigDecimal(0)));
        super.setMinimumBalance(new Money(new BigDecimal(0)));
    }

    public StudentChecking(Money balance, Integer secretKey, AccountHolder primaryOwner, AccountStatus status) {
        this(balance, secretKey, primaryOwner, null, status);
    }
}
