package com.ironhack.midterm.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Integer id;
    private LocalDateTime realizedAt;
    @ManyToOne
    private Checking account;
    @ManyToOne
    private User user;

    public Transaction() { }

    public Transaction(Checking account, User user) {
        this.account = account;
        this.user = user;
        this.realizedAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getRealizedAt() { return realizedAt; }
}
