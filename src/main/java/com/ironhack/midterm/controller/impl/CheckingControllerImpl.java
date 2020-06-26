package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.controller.interfaces.CheckingController;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.service.CheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckingControllerImpl implements CheckingController {
    @Autowired
    private CheckingService checkingService;

    @PostMapping("/accounts/checkings")
    @ResponseStatus(HttpStatus.CREATED)
    public Checking create(@RequestBody AccountInstance accountInstance,
                           @RequestParam(value = "secondaryOwnerId", required = false) Integer secondaryOwnerId) {
        return checkingService.create(accountInstance, secondaryOwnerId);
    }
}
