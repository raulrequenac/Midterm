package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.AccountHolderInstance;
import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Address;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.Savings;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.AddressRepository;
import com.ironhack.midterm.repository.SavingsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SavingsServiceTest {
    @Autowired
    private SavingsService savingsService;
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountHolderService accountHolderService;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDetailsServiceImpl userService;

    @AfterEach
    public void tearDown() {
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    public void create() {
        Address a = new Address();
        addressService.create(a);
        AccountHolderInstance aI = new AccountHolderInstance("a", "a", "a", LocalDate.now(), a.getId());
        AccountHolder ah = accountHolderService.create(aI);
        userService.login(ah);
        AccountInstance ac = new AccountInstance(new BigDecimal(300), Currency.getInstance("USD"), ah.getId(), 1234);
        Savings s = savingsService.create(ac, null, null, null);
        assertDoesNotThrow(() -> accountService.findById(ah, s.getId()));
    }
}