package com.ironhack.midterm.service;

import com.ironhack.midterm.MidtermApplication;
import com.ironhack.midterm.controller.dto.CreditCardInstance;
import com.ironhack.midterm.exceptions.ForbiddenAccessException;
import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.exceptions.NotLoggedInException;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.CreditCard;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.repository.CreditCardRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreditCardService {
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private AccountHolderService accountHolderService;

    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public CreditCard create(CreditCardInstance creditCardInstance, BigDecimal interestRate, BigDecimal creditLimit, Integer secondaryOwnerId) {
        LOGGER.info("[INIT] - Create CreditCard Account");
        AccountHolder owner = accountHolderService.findById(creditCardInstance.getPrimaryOwnerId());
        Money balance = new Money(creditCardInstance.getAmount(), creditCardInstance.getCurrency());
        CreditCard creditCard = new CreditCard(balance, owner);
        if (secondaryOwnerId!=null) creditCard.setSecondaryOwner(accountHolderService.findById(secondaryOwnerId));
        if (interestRate!=null) creditCard.setInterestRate(interestRate);
        if (creditLimit!=null) creditCard.setCreditLimit(creditLimit);
        CreditCard cc = creditCardRepository.save(creditCard);
        LOGGER.info("[END] - Create CreditCard Account");
        return cc;
    }
}
