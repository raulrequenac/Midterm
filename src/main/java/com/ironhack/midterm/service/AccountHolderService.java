package com.ironhack.midterm.service;

import com.ironhack.midterm.MidtermApplication;
import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Role;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.RoleRepository;
import com.ironhack.midterm.util.PasswordUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountHolderService {
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private RoleRepository roleRepository;

    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public AccountHolder findById(Integer id) {
        LOGGER.info("[INIT] - Find AccountHolder with id: "+id);
        AccountHolder accountHolder = accountHolderRepository.findById(id).orElseThrow(() -> new IdNotFoundException("user", id));
        LOGGER.info("[END] - Find AccountHolder with id: "+id);
        return accountHolder;
    }

    public AccountHolder create(AccountHolder accountHolder) {
        LOGGER.info("[INIT] - Create AccountHolder User");
        accountHolder.setPassword(PasswordUtility.encryptPassword(accountHolder.getPassword()));
        roleRepository.save(new Role("ROLE_ACCOUNT_HOLDER", accountHolder));
        AccountHolder a = accountHolderRepository.save(accountHolder);
        LOGGER.info("[END] - Create AccountHolder User");
        return a;
    }
}
