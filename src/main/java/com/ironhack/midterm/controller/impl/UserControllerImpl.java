package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.UserController;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.ThirdParty;
import com.ironhack.midterm.service.AccountHolderService;
import com.ironhack.midterm.service.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController {
    @Autowired
    private AccountHolderService accountHolderService;
    @Autowired
    private ThirdPartyService thirdPartyService;

    @PostMapping("/users/account-holders")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody AccountHolder accountHolder) {
        return accountHolderService.create(accountHolder);
    }

    @PostMapping("/users/third-parties")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestBody ThirdParty thirdParty) {
        return thirdPartyService.create(thirdParty);
    }
}
