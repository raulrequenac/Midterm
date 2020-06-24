package com.ironhack.midterm.service;

import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.util.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountHolderService {
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    public AccountHolder findById(Integer id) {
        return accountHolderRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
    }

    public AccountHolder findByName(String name) {
        return accountHolderRepository.findByName(name);
    }

    public AccountHolder create(AccountHolder accountHolder) {
        accountHolder.setPassword(PasswordUtility.encryptPassword(accountHolder.getPassword()));
        return accountHolderRepository.save(accountHolder);
    }
}
