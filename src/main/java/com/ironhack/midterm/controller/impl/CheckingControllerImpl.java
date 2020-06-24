package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.controller.dto.Transference;
import com.ironhack.midterm.controller.interfaces.CheckingController;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.service.CheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckingControllerImpl implements CheckingController {
    @Autowired
    private CheckingService checkingService;

    @GetMapping("/accounts/checkings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Checking findById(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        return checkingService.findById(user, id);
    }

    @GetMapping("/accounts/checkings/{id}/balance")
    @ResponseStatus(HttpStatus.OK)
    public Money findBalance(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        return checkingService.findBalance(user, id);
    }

    @PostMapping("/accounts/checkings/{id}/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transfer(@AuthenticationPrincipal User user, @PathVariable Integer id, @RequestBody Transference transference) {
        checkingService.transfer(user, id, transference);
    }

    @PostMapping("/accounts/checkings")
    @ResponseStatus(HttpStatus.CREATED)
    public Checking create(@RequestBody AccountInstance accountInstance,
                           @RequestParam(value = "secondaryOwnerId", required = false) Integer secondaryOwnerId) {
        return checkingService.create(accountInstance, secondaryOwnerId);
    }

    @PostMapping("/accounts/checkings/{id}/credit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void credit(@AuthenticationPrincipal User user, @PathVariable Integer id, @RequestBody Money amount) {
        checkingService.credit(user, id, amount);
    }

    @PostMapping("/accounts/checkings/{id}/debit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debit(@AuthenticationPrincipal User user, @PathVariable Integer id, @RequestBody Money amount) {
        checkingService.credit(user, id, amount);
    }
}
