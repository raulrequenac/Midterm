package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.dto.AccountHolderInstance;
import com.ironhack.midterm.controller.interfaces.UserController;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Admin;
import com.ironhack.midterm.model.ThirdParty;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController {
    @Autowired
    private UserDetailsServiceImpl userService;
    @Autowired
    private AccountHolderService accountHolderService;
    @Autowired
    private ThirdPartyService thirdPartyService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/users/login")
    @ResponseStatus(HttpStatus.OK)
    public User login(@AuthenticationPrincipal User user) {
        return userService.login(user);
    }

    @PostMapping("/users/logout")
    @ResponseStatus(HttpStatus.OK)
    public User logout(@AuthenticationPrincipal User user) {
        return userService.logout(user);
    }

    @PostMapping("/users/account-holders")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody AccountHolderInstance accountHolderInstance) {
        return accountHolderService.create(accountHolderInstance);
    }

    @PostMapping("/users/third-parties")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestBody ThirdParty thirdParty) {
        return thirdPartyService.create(thirdParty);
    }

    @PostMapping("/users/admins")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.create(admin);
    }
}
