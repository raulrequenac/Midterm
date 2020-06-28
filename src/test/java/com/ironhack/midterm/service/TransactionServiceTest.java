package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.AccountHolderInstance;
import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.exceptions.FraudDetectedException;
import com.ironhack.midterm.model.*;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.AddressRepository;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.repository.TransactionRepository;
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
class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CheckingService checkingService;
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private AccountHolderService accountHolderService;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private UserDetailsServiceImpl userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;

    private Checking account;
    private AccountHolder user;

    @BeforeEach
    public void setUp() {
        Address address = new Address();
        addressService.create(address);
        AccountHolderInstance aI = new AccountHolderInstance("a", "a", "a", LocalDate.now().minusYears(25), address.getId());
        user = accountHolderService.create(aI);
        AccountHolder s  = accountHolderService.create(aI);
        userService.login(user);
        AccountInstance ac = new AccountInstance(new BigDecimal(200), Currency.getInstance("USD"), user.getId(), 1234);
        account = checkingService.create(ac, s.getId());
    }

    @AfterEach
    public void tearDown() {
        transactionRepository.deleteAll();
        checkingRepository.deleteAll();
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    public void create() {
        Transaction transaction = transactionService.create(user, account);
        assertTrue(transactionRepository.findById(transaction.getId()).isPresent());
    }

    @Test
    public void isFraud_false() {
        assertDoesNotThrow(() -> transactionService.isFraud(user, account));
    }

    @Test
    public void isFraud_true() {
        for (int i=4; i>0; i--) transactionService.create(user, account);
        assertThrows(FraudDetectedException.class, () -> transactionService.isFraud(user, account));
    }
}