package com.ironhack.midterm.service;

import com.ironhack.midterm.model.ThirdParty;
import com.ironhack.midterm.repository.ThirdPartyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ThirdPartyServiceTest {
    @Autowired
    private ThirdPartyService thirdPartyService;
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private UserDetailsServiceImpl userService;

    @AfterEach
    public void tearDown() {
        thirdPartyRepository.deleteAll();
    }

    @Test
    public void create() {
        ThirdParty thirdParty = new ThirdParty("thirdParty", "thirdParty", "thirdParty", "thirdParty");
        thirdPartyService.create(thirdParty);
        assertDoesNotThrow(() -> userService.findById(thirdParty.getId()));
    }
}