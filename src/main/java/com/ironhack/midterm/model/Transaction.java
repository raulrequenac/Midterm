package com.ironhack.midterm.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Integer id;
    private LocalDate realizedAt;
    @ManyToOne
    private Checking account;

    public Transaction(Checking account) {
        this.account = account;
        this.realizedAt = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getRealizedAt() {
        return realizedAt;
    }

    public void setRealizedAt(LocalDate realizedAt) {
        this.realizedAt = realizedAt;
    }

    public Checking getAccount() {
        return account;
    }

    public void setAccount(Checking account) {
        this.account = account;
    }
}
