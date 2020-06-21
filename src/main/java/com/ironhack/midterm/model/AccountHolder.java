package com.ironhack.midterm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class AccountHolder {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Date dateOfBirth;
    private Address address;
    private Address mailingAddress;

    public AccountHolder(String name, Date dateOfBirth, Address address, Address mailingAddress) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.mailingAddress = mailingAddress;
    }

    public AccountHolder(String name, Date dateOfBirth, Address address) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.mailingAddress = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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
}
