package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.CreditCardInstance;
import com.ironhack.midterm.exceptions.ForbiddenAccessException;
import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.exceptions.NotLoggedInException;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.CreditCard;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreditCardService {
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private AccountHolderService accountHolderService;

    public CreditCard findById(User user, Integer id) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        CreditCard creditCard =  creditCardRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
        if (!user.canAccess(creditCard)) throw new ForbiddenAccessException();
        return creditCard;
    }

    public Money findBalance(User user, Integer id) {
        return findById(user, id).getBalance();
    }

    public CreditCard create(CreditCardInstance creditCardInstance, BigDecimal interestRate, BigDecimal creditLimit, Integer secondaryOwnerId) {
        AccountHolder owner = accountHolderService.findById(creditCardInstance.getPrimaryOwnerId());
        CreditCard creditCard = new CreditCard(creditCardInstance.getBalance(), owner);
        if (secondaryOwnerId!=null) creditCard.setSecondaryOwner(accountHolderService.findById(secondaryOwnerId));
        if (interestRate!=null) creditCard.setInterestRate(interestRate);
        if (creditLimit!=null) creditCard.setCreditLimit(creditLimit);
        return creditCard;
    }

    public void credit(User user, Integer id, Money amount) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        CreditCard creditCard = findById(user, id);
        if (!user.canAccess(creditCard)) throw new ForbiddenAccessException();
        creditCard.credit(amount);
        creditCardRepository.save(creditCard);
    }

    public void debit (User user, Integer id, Money amount) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        CreditCard creditCard = findById(user, id);
        if (!user.canAccess(creditCard)) throw new ForbiddenAccessException();
        creditCard.debit(amount);
        creditCardRepository.save(creditCard);
    }
}
