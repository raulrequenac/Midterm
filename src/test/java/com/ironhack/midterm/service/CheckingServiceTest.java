package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.AccountHolderInstance;
import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Address;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.AddressRepository;
import com.ironhack.midterm.repository.CheckingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckingServiceTest {
    @Autowired
    private CheckingService checkingService;
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private AccountHolderService accountHolderService;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDetailsServiceImpl userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;

    private AccountHolder accountHolder;
    private Address address;

    @BeforeEach
    public void setUp() {
        address = new Address();
        addressService.create(address);
        AccountHolderInstance aI = new AccountHolderInstance("user", "user", "user", LocalDate.now(), address.getId());
        accountHolder = accountHolderService.create(aI);
    }

    @AfterEach
    public void tearDown() {
        checkingRepository.deleteAll();
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    public void createStudentChecking() {
        userService.login(accountHolder);
        AccountInstance ac = new AccountInstance(new BigDecimal(200), Currency.getInstance("USD"), accountHolder.getId(), 1234);
        Checking checking = checkingService.create(ac, null);
        assertEquals(checking.getBalance().toString(), accountService.findById(accountHolder, checking.getId()).getBalance().toString());
    }

    @Test
    public void createChecking() {
        AccountHolderInstance aI2 = new AccountHolderInstance("a", "a", "a", LocalDate.now().minusYears(25), address.getId());
        AccountHolder s = accountHolderService.create(aI2);
        userService.login(accountHolder);
        AccountInstance ac = new AccountInstance(new BigDecimal(200), Currency.getInstance("USD"), accountHolder.getId(), 1234);
        Checking checking = checkingService.create(ac, s.getId());
        assertEquals(checking.getBalance().toString(), accountService.findById(accountHolder, checking.getId()).getBalance().toString());
    }
}