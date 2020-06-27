package com.ironhack.midterm.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.controller.dto.Transference;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.service.AccountService;
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
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountControllerImplTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AccountService accountService;

    private MockMvc mockMvc;
    private Checking checking;
    private Money balance;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        balance = new Money(new BigDecimal(100));
        checking = new Checking(balance, 1234, null);
        checking.setId(1);
        when(accountService.findById(any(), any())).thenReturn(checking);
        when(accountService.findBalance(any(), any())).thenReturn(new Money(new BigDecimal(100)));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void findById() throws Exception {
        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void findBalance() throws Exception {
        mockMvc.perform(get("/accounts/1/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value("100.0"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void credit() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/accounts/1/credit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(balance)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void debit() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/accounts/1/debit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(balance)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void unfreeze() throws Exception {
        mockMvc.perform(post("/accounts/1/unfreeze"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void transfer() throws Exception {
        Transference transference = new Transference(1, "user", Currency.getInstance("USD"), new BigDecimal(100));
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/accounts/1/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transference)))
                .andExpect(status().isNoContent());
    }
}