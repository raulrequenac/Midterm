package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.CreditCardInstance;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Address;
import com.ironhack.midterm.model.CreditCard;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountHolderService accountHolderService;
    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserDetailsServiceImpl userService;

    private AccountHolder user;
    private CreditCard creditCard;

    @BeforeEach
    public void setUp() {
        Address address = new Address();
        addressRepository.save(address);
        user = accountHolderService.create(new AccountHolder("user", "user", "user", LocalDate.of(1997, 10, 22), address));
        userService.login(user);
        creditCard = creditCardService.create(new CreditCardInstance(new Money(new BigDecimal(300)), user.getId()), null, null, null);
    }

    @Test
    public void findById() {
        assertEquals(CreditCard.class, accountService.findById(user, 1).getClass());
    }
}