package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.AccountHolderInstance;
import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.exceptions.NameNotFoundException;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Address;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.AddressRepository;
import com.ironhack.midterm.repository.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountHolderServiceTest {
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private AccountHolderService accountHolderService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RoleRepository roleRepository;

    private Address address;
    private AccountHolder accountHolder;

    @BeforeEach
    public void setUp() {
        address = new Address("Spain", "Malaga", 29005, "Larios", Short.valueOf("10"), Short.valueOf("4"), "D");
        addressService.create(address);
        AccountHolderInstance accountHolderInstance = new AccountHolderInstance("a", "a", "a", LocalDate.now(), address.getId());
        accountHolder = accountHolderService.create(accountHolderInstance);
    }

    @AfterEach
    public void tearDown() {
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    public void findById_Existing_ReturnAccountHolder(){
        assertEquals(accountHolder.getName(), accountHolderService.findById(accountHolder.getId()).getName());
    }

    @Test
    public void findById_NotExisting_ThrowsError(){
        assertThrows(IdNotFoundException.class, () -> accountHolderService.findById(200));
    }

    @Test
    public void create() {
        AccountHolderInstance aI = new AccountHolderInstance("b", "b", "b", LocalDate.now(), address.getId());
        AccountHolder a = accountHolderService.create(aI);
        assertEquals("b", accountHolderService.findById(a.getId()).getName());
    }
}