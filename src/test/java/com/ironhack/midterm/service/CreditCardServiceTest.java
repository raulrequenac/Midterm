package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.AccountHolderInstance;
import com.ironhack.midterm.controller.dto.CreditCardInstance;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Address;
import com.ironhack.midterm.model.CreditCard;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.AddressRepository;
import com.ironhack.midterm.repository.CreditCardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreditCardServiceTest {
    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountHolderService accountHolderService;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private UserDetailsServiceImpl userService;
    @Autowired
    private AccountService accountService;

    @AfterEach
    public void tearDown() {
        creditCardRepository.deleteAll();
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    public void create() {
        Address address = new Address();
        addressService.create(address);
        AccountHolderInstance aI = new AccountHolderInstance("user", "user", "user", LocalDate.of(1997, 10, 22), address.getId());
        AccountHolder user = accountHolderService.create(aI);
        userService.login(user);
        CreditCard creditCard = creditCardService.create(new CreditCardInstance(new BigDecimal(300), Currency.getInstance("USD"), user.getId()), null, null, null);
        assertEquals(creditCard.getBalance().getAmount(), accountService.findById(user, creditCard.getId()).getBalance().getAmount());
    }
}