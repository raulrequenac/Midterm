package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.AccountHolderInstance;
import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.controller.dto.Transference;
import com.ironhack.midterm.exceptions.AlreadyActiveException;
import com.ironhack.midterm.exceptions.ForbiddenAccessException;
import com.ironhack.midterm.exceptions.FraudDetectedException;
import com.ironhack.midterm.exceptions.NameNotFoundException;
import com.ironhack.midterm.model.*;
import com.ironhack.midterm.repository.*;
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
class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountHolderService accountHolderService;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SavingsService savingsService;
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserDetailsServiceImpl userService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionService transactionService;

    private Address address;
    private AccountHolder user;
    private AccountHolder secondUser;
    private Savings account;

    @BeforeEach
    public void setUp() {
        address = new Address();
        addressRepository.save(address);
        AccountHolderInstance accountHolderInstance = new AccountHolderInstance("user", "user", "user", LocalDate.of(1997, 10, 22), address.getId());
        user = accountHolderService.create(accountHolderInstance);
        userService.login(user);
        AccountHolderInstance accountHolderInstance2 = new AccountHolderInstance("secondUser", "user", "user", LocalDate.of(1997, 10, 22), address.getId());
        secondUser = accountHolderService.create(accountHolderInstance);
        account = savingsService.create(new AccountInstance(new BigDecimal(300), Currency.getInstance("USD"), user.getId(), 1234), null, null, secondUser.getId());
    }

    @AfterEach
    public void tearDown() {
        transactionRepository.deleteAll();
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    public void findById() {
        assertDoesNotThrow(() -> accountService.findById(user, account.getId()));
    }

    @Test
    public void findByIdAndOwnerName() {
        assertDoesNotThrow(() -> accountService.findByIdAndOwnerName(secondUser.getName(), account.getId()));
    }

    @Test
    public void findByIdAndOwnerName_wrongName() {
        assertThrows(NameNotFoundException.class, () -> accountService.findByIdAndOwnerName("ee", account.getId()));
    }

    @Test
    public void findBalance() {
        assertEquals(account.getBalance().getAmount(), accountService.findBalance(user, account.getId()).getAmount());
    }

    @Test
    public void credit() {
        accountService.credit(user, account.getId(), new Money(new BigDecimal(30)), null);
        assertEquals(account.getBalance().getAmount().add(new BigDecimal(30)), accountService.findBalance(user, account.getId()).getAmount());
    }

    @Test
    public void credit_cannotAccess() {
        AccountHolderInstance aI = new AccountHolderInstance("user", "user", "user", LocalDate.of(1997, 10, 22), address.getId());
        AccountHolder otherUser = accountHolderService.create(aI);
        userService.login(otherUser);
        assertThrows(ForbiddenAccessException.class, () -> accountService.credit(otherUser, account.getId(), new Money(new BigDecimal(30)), null));
    }

    @Test
    public void debit() {
        accountService.debit(user, account.getId(), new Money(new BigDecimal(30)), null);
        assertEquals(account.getBalance().getAmount().subtract(new BigDecimal(30)), accountService.findBalance(user, account.getId()).getAmount());
    }

    @Test
    public void debit_cannotAccess() {
        AccountHolderInstance aI = new AccountHolderInstance("user", "user", "user", LocalDate.of(1997, 10, 22), address.getId());
        AccountHolder otherUser = accountHolderService.create(aI);
        userService.login(otherUser);
        assertThrows(ForbiddenAccessException.class, () -> accountService.debit(otherUser, account.getId(), new Money(new BigDecimal(30)), null));
    }

    @Test
    public void transfer() {
        Savings account2 = savingsService.create(new AccountInstance(new BigDecimal(300), Currency.getInstance("USD"), user.getId(), 1234), null, null, null);
        accountService.transfer(user, account.getId(), new Transference(account2.getId(), user.getName(), Currency.getInstance("USD"), new BigDecimal(100)));
        assertEquals(account2.getBalance().getAmount().add(new BigDecimal(100)), accountService.findBalance(user, account2.getId()).getAmount());
        assertEquals(account.getBalance().getAmount().subtract(new BigDecimal(100)), accountService.findBalance(user, account.getId()).getAmount());
    }

    @Test
    public void unfreeze() {
        try {
            for (int i = 0; i < 4; i++)
                accountService.credit(user, account.getId(), new Money(new BigDecimal(10)), null);
        } catch (FraudDetectedException e) {
            assertDoesNotThrow(() -> accountService.unfreeze(user, account.getId()));
        }
    }

    @Test
    public void unfreeze_AlreadyActive() {
        assertThrows(AlreadyActiveException.class, () -> accountService.unfreeze(user, account.getId()));
    }

    @Test
    public void canAccess_false() {
        AccountHolderInstance aI = new AccountHolderInstance("user", "user", "user", LocalDate.of(1997, 10, 22), address.getId());
        AccountHolder otherUser = accountHolderService.create(aI);
        assertFalse(accountService.canAccess(account.getId(), otherUser));
    }
}