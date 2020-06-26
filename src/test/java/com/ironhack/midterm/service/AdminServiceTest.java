package com.ironhack.midterm.service;

import com.ironhack.midterm.model.Admin;
import com.ironhack.midterm.repository.AdminRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserDetailsServiceImpl userService;

    @AfterEach
    public void tearDown() {
        adminRepository.deleteAll();
    }

    @Test
    public void create() {
        Admin admin = new Admin("admin", "admin", "admin");
        adminService.create(admin);
        assertDoesNotThrow(() -> userService.findById(admin.getId()));
    }
}