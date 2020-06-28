package com.ironhack.midterm.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class AccountHolder extends User {
    @NotNull
    private LocalDate dateOfBirth;
    @ManyToOne
    private Address address;
    @ManyToOne
    private Address mailingAddress;
    @OneToMany(mappedBy = "primaryOwner", cascade = CascadeType.ALL)
    private List<Checking> primaryAccounts;
    @OneToMany(mappedBy = "secondaryOwner", cascade = CascadeType.ALL)
    private List<Checking> secondaryAccounts;

    public AccountHolder() {
    }

    public AccountHolder(String name, String username, String password, LocalDate dateOfBirth, Address address) {
        super(name, username, password);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        primaryAccounts = new ArrayList<>();
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
