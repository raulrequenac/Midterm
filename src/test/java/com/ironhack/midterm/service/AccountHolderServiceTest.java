package com.ironhack.midterm.service;

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
    private AddressRepository addressRepository;
    @Autowired
    private RoleRepository roleRepository;

    private Address address;
    private AccountHolder accountHolder;

    @BeforeEach
    public void setUp() {
        address = new Address();
        addressRepository.save(address);
        accountHolder = new AccountHolder("a", "a", "a", LocalDate.now(), address);
        accountHolderService.create(accountHolder);
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

    @Test void findByName_Existing_ReturnAccountHolder() {
        assertEquals(accountHolder.getName(), accountHolderService.findByName(accountHolder.getName()).getName());
    }

    @Test
    public void findByName_NotExisting_ThrowsError(){
        assertThrows(NameNotFoundException.class, () -> accountHolderService.findByName("lukhuhñh"));
    }

    @Test
    public void create() {
        AccountHolder a = new AccountHolder("b", "b", "b", LocalDate.now(), address);
        accountHolderService.create(a);
        assertEquals("b", accountHolderService.findById(a.getId()).getName());
    }


}