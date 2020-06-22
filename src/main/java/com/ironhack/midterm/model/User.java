package com.ironhack.midterm.model;

import com.ironhack.midterm.exceptions.AlreadyLoggedInException;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    private String name;
    private boolean loggedIn;

    public User() {
    }

    public User(String name) {
        this.name = name;
        this.loggedIn = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void logIn() {
        if (this.loggedIn) throw new AlreadyLoggedInException();
        this.loggedIn = true;
    }

    public void logOut() {
        this.loggedIn = false;
    }

    public abstract boolean canAccess(Checking account);
}
