package com.ironhack.midterm.controller.dto;

import java.time.LocalDate;

public class AccountHolderInstance {
    private String name;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private Integer addressId;

    public AccountHolderInstance(String name, String username, String password, LocalDate dateOfBirth, Integer addressId) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Integer getAddressId() {
        return addressId;
    }
}
