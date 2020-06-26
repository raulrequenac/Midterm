package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.CheckingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    private AccountHolder accountHolder;

    @AfterEach
    public void tearDown() {
        checkingRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    public void createStudentChecking() {
        accountHolder = new AccountHolder("a", "a", "a", LocalDate.now(), null);
        accountHolderService.create(accountHolder);
        userService.login(accountHolder);
        AccountInstance ac = new AccountInstance(new Money(new BigDecimal(200)), accountHolder.getId(), 1234);
        Checking checking = checkingService.create(ac, null);
        assertEquals(checking.getBalance().toString(), accountService.findById(accountHolder, checking.getId()).getBalance().toString());
    }

    @Test
    public void createChecking() {
        accountHolder = new AccountHolder("a", "a", "a", LocalDate.now().minusYears(25), null);
        AccountHolder s = accountHolder = new AccountHolder("a", "a", "a", LocalDate.now().minusYears(25), null);
        accountHolderService.create(accountHolder);
        accountHolderService.create(s);
        userService.login(accountHolder);
        AccountInstance ac = new AccountInstance(new Money(new BigDecimal(200)), accountHolder.getId(), 1234);
        Checking checking = checkingService.create(ac, s.getId());
        assertEquals(checking.getBalance().toString(), accountService.findById(accountHolder, checking.getId()).getBalance().toString());
    }
}