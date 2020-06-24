package com.ironhack.midterm.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class AccountHolder extends User {
    @Id
    @GeneratedValue
    private Integer id;
    private LocalDate dateOfBirth;
    @ManyToOne
    private Address address;
    @ManyToOne
    private Address mailingAddress;
    @OneToMany(mappedBy = "primaryOwner")
    private List<Checking> primaryAccounts;
    @OneToMany(mappedBy = "secondaryOwner")
    private List<Checking> secondaryAccounts;

    public AccountHolder() {
    }

    public AccountHolder(String name, String username, String password, LocalDate dateOfBirth, Address address) {
        super(name, username, password);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public boolean canAccess(Checking account) {
        return primaryAccounts.stream().anyMatch(acc -> acc.getId()==account.getId()) ||
                secondaryAccounts.stream().anyMatch(acc -> acc.getId()==account.getId());
    }

    public List<Checking> getPrimaryAccounts() {
        return primaryAccounts;
    }

    public void setPrimaryAccounts(List<Checking> primaryAccounts) {
        this.primaryAccounts = primaryAccounts;
    }

    public List<Checking> getSecondaryAccounts() {
        return secondaryAccounts;
    }

    public void setSecondaryAccounts(List<Checking> secondaryAccounts) {
        this.secondaryAccounts = secondaryAccounts;
    }
}
