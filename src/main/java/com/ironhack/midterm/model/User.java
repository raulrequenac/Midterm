package com.ironhack.midterm.model;

import com.ironhack.midterm.exceptions.AlreadyLoggedInException;
import com.ironhack.midterm.exceptions.AlreadyLoggedOutException;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    private boolean loggedIn;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL, mappedBy="user")
    protected Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "user")
    protected List<Transaction> transactions;

    public User() { }

    public User(String name, String username, String password) {
        this.name = name;
        this.loggedIn = false;
        this.username = username;
        this.password = password;
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void logIn() {
        if (this.loggedIn) throw new AlreadyLoggedInException();
        this.loggedIn = true;
    }

    public void logOut() {
        if (!this.loggedIn) throw new AlreadyLoggedOutException();
        this.loggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
