package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.dto.CreditCardInstance;
import com.ironhack.midterm.controller.interfaces.CreditCardController;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.CreditCard;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class CreditCardControllerImpl implements CreditCardController {
    @Autowired
    private CreditCardService creditCardService;

    @PostMapping("/accounts/credit-cards")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard create(@RequestBody CreditCardInstance creditCardInstance,
                             @RequestParam(value = "interestRate", required = false) BigDecimal interestRate,
                             @RequestParam(value = "creditLimit", required = false) BigDecimal creditLimit,
                             @RequestParam(value = "secondaryOwnerId", required = false) Integer secondaryOwnerId) {
        return creditCardService.create(creditCardInstance, interestRate, creditLimit, secondaryOwnerId);
    }
}
