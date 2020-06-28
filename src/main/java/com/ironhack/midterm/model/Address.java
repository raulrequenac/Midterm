package com.ironhack.midterm.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String country;
    private String city;
    private Integer postCode;
    private String street;
    private Short portalNumber;
    private Short floor;
    private String door;
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<AccountHolder> users;
    @OneToMany(mappedBy = "mailingAddress", cascade = CascadeType.ALL)
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
