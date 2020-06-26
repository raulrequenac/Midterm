package com.ironhack.midterm.service;

import com.ironhack.midterm.exceptions.AlreadyLoggedInException;
import com.ironhack.midterm.exceptions.AlreadyLoggedOutException;
import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.model.Admin;
import com.ironhack.midterm.repository.AdminRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDetailsServiceImplTest {
    @Autowired
    private UserDetailsServiceImpl userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;

    private Admin admin;

    @BeforeEach
    public void setUp() {
        admin = new Admin("admin", "admin", "admin");
        adminService.create(admin);
    }

    @AfterEach
    public void tearDown() {
        adminRepository.deleteAll();
    }

    @Test
    public void findById_Existing_ReturnUser() {
        assertDoesNotThrow(() -> userService.findById(admin.getId()));
    }

    @Test
    public void findById_NotExisting_ThrowError() {
        assertThrows(IdNotFoundException.class, () -> userService.findById(200));
    }

    @Test
    public void loadUserByUsername_Existing_ReturnUserDetails() {
        assertDoesNotThrow(() -> userService.loadUserByUsername(admin.getUsername()));
    }

    @Test
    public void loadUserByUsername_NotExisting_ThrowsError() {
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("aaaaaa"));
    }

    @Test
    public void login() {
        assertTrue(userService.login(admin).isLoggedIn());
    }

    @Test
    public void login_AlreadyLoggedIn() {
        userService.login(admin);
        assertThrows(AlreadyLoggedInException.class, () -> userService.login(admin));
    }

    @Test
    public void logout() {
        userService.login(admin);
        assertFalse(userService.logout(admin).isLoggedIn());
    }

    @Test
    public void logout_AlreadyLoggedOut() {
        assertThrows(AlreadyLoggedOutException.class, () -> userService.logout(admin));
    }
}