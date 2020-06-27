package com.ironhack.midterm.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.model.CreditCard;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.Savings;
import com.ironhack.midterm.service.CreditCardService;
import com.ironhack.midterm.service.SavingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CreditCardControllerImplTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CreditCardService creditCardService;

    private MockMvc mockMvc;
    private CreditCard creditCard;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        creditCard = new CreditCard(new Money(new BigDecimal(100)), null);
        creditCard.setId(1);
        when(creditCardService.create(any(), any(), any(), any())).thenReturn(creditCard);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void create() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/accounts/credit-cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creditCard)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"));
    }
}