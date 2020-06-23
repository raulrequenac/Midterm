package com.ironhack.midterm.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    public AccountHolder(String name) {
        super(name);
    }

    public AccountHolder(String name, LocalDate dateOfBirth, Address address, Address mailingAddress) {
        super(name);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.mailingAddress = mailingAddress;
    }

    public AccountHolder(String name, LocalDate dateOfBirth, Address address) {
        this(name, dateOfBirth, address, null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
