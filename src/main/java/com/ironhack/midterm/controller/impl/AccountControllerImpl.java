package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.dto.Transference;
import com.ironhack.midterm.controller.interfaces.AccountController;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountControllerImpl implements AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts/checkings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Checking findById(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        return accountService.findById(user, id);
    }

    @GetMapping("/accounts/checkings/{id}/balance")
    @ResponseStatus(HttpStatus.OK)
    public Money findBalance(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        return accountService.findBalance(user, id);
    }

    @PostMapping("/accounts/checkings/{id}/credit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void credit(@AuthenticationPrincipal User user, @PathVariable Integer id, @RequestBody Money amount) {
        accountService.credit(user, id, amount);
    }

    @PostMapping("/accounts/checkings/{id}/debit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debit(@AuthenticationPrincipal User user, @PathVariable Integer id, @RequestBody Money amount) {
        accountService.debit(user, id, amount);
    }

    @PostMapping("/accounts/checkings/{id}/unfreeze")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfreeze(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        accountService.unfreeze(user, id);
    }

    @PostMapping("/accounts/{id}/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transfer(@AuthenticationPrincipal User user, @PathVariable Integer id, @RequestBody Transference transference) {
        accountService.transfer(user, id, transference);
    }
}
