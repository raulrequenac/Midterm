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
}
