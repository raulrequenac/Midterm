package com.ironhack.midterm.service;

import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.exceptions.NameNotFoundException;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Role;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.RoleRepository;
import com.ironhack.midterm.util.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountHolderService {
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private RoleRepository roleRepository;

    public AccountHolder findById(Integer id) {
        return accountHolderRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
    }

    public AccountHolder findByName(String name) {
        AccountHolder accountHolder = accountHolderRepository.findByName(name);
        if (accountHolder==null) throw new NameNotFoundException();
        return accountHolder;
    }

    public AccountHolder create(AccountHolder accountHolder) {
        accountHolder.setPassword(PasswordUtility.encryptPassword(accountHolder.getPassword()));
        roleRepository.save(new Role("ACCOUNT_HOLDER", accountHolder));
        return accountHolderRepository.save(accountHolder);
    }
}
