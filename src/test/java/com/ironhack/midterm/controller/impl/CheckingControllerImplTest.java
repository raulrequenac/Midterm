package com.ironhack.midterm.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.service.CheckingService;
import com.ironhack.midterm.service.CheckingService;
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
class CheckingControllerImplTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CheckingService checkingService;

    private MockMvc mockMvc;
    private Checking checking;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        checking = new Checking(new Money(new BigDecimal(100)), 1234, null);
        checking.setId(1);
        when(checkingService.create(any(), any())).thenReturn(checking);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void create() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/accounts/checkings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checking)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"));
    }
}