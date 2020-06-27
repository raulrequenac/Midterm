package com.ironhack.midterm.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.model.*;
import com.ironhack.midterm.service.*;
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
class UserControllerImplTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserDetailsServiceImpl userService;
    @MockBean
    private AccountHolderService accountHolderService;
    @MockBean
    private ThirdPartyService thirdPartyService;
    @MockBean
    private AdminService adminService;

    private MockMvc mockMvc;
    private AccountHolder accountHolder;
    private ThirdParty thirdParty;
    private Admin admin;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        accountHolder = new AccountHolder();
        thirdParty = new ThirdParty("", "", "", "");
        admin = new Admin("", "", "");
        accountHolder.setId(1);
        thirdParty.setId(1);
        admin.setId(1);
        when(userService.login(any())).thenReturn(accountHolder);
        when(userService.logout(any())).thenReturn(accountHolder);
        when(accountHolderService.create(any())).thenReturn(accountHolder);
        when(thirdPartyService.create(any())).thenReturn(thirdParty);
        when(adminService.create(any())).thenReturn(admin);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void login() throws Exception {
        mockMvc.perform(post("/users/login"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void logout() throws Exception {
        mockMvc.perform(post("/users/logout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createAccountHolder() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/users/account-holders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountHolder)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createThirdParty() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/users/third-parties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(thirdParty)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createAdmin() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"));
    }
}