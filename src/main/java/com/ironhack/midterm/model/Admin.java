package com.ironhack.midterm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Admin extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Admin() {
    }

    public Admin(String name) {
        super(name);
    }

    @Override
    public boolean canAccess(Checking account) {
        return true;
    }

    //createAccount (Checking, Saving or CreditCard)
}
