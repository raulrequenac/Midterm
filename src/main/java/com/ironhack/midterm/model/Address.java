package com.ironhack.midterm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private Integer id;
    private String country;
    private String city;
    private Integer postCode;
    private String street;
    private Short portalNumber;
    private Short floor;
    private String door;
    @OneToMany(mappedBy = "address")
    private List<AccountHolder> users;
    @OneToMany(mappedBy = "mailingAddress")
    private List<AccountHolder> mailingUsers;

    public Address() {
    }

    public Address(String country, String city, Integer postCode, String street, Short portalNumber, Short floor, String door) {
        this.country = country;
        this.city = city;
        this.postCode = postCode;
        this.street = street;
        this.portalNumber = portalNumber;
        this.floor = floor;
        this.door = door;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Short getPortalNumber() {
        return portalNumber;
    }

    public void setPortalNumber(Short portalNumber) {
        this.portalNumber = portalNumber;
    }

    public Short getFloor() {
        return floor;
    }

    public void setFloor(Short floor) {
        this.floor = floor;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }
}
