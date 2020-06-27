package com.ironhack.midterm.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.Savings;
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

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
class SavingsControllerImplTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SavingsService savingsService;

    private MockMvc mockMvc;
    private Savings savings;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        savings = new Savings(new Money(new BigDecimal(100)), 1234, null);
        savings.setId(1);
        when(savingsService.create(any(), any(), any(), any())).thenReturn(savings);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void create() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/accounts/savings")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(savings)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"));
    }
}