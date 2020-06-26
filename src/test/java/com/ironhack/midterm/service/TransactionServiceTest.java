package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.exceptions.FraudDetectedException;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.Transaction;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    private Checking account;
    private AccountHolder user;

    @BeforeEach
    public void setUp() {
        user = new AccountHolder("a", "a", "a", LocalDate.now().minusYears(25), null);
        AccountHolder s  = new AccountHolder("a", "a", "a", LocalDate.now().minusYears(25), null);
        accountHolderService.create(user);
        accountHolderService.create(s);
        userService.login(user);
        AccountInstance ac = new AccountInstance(new Money(new BigDecimal(200)), user.getId(), 1234);
        account = checkingService.create(ac, s.getId());
    }

    @AfterEach
    public void tearDown() {
        transactionRepository.deleteAll();
        checkingRepository.deleteAll();
        accountHolderRepository.deleteAll();
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