package com.ironhack.midterm.service;

import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.controller.dto.Transference;
import com.ironhack.midterm.exceptions.ForbiddenAccessException;
import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.exceptions.NotLoggedInException;
import com.ironhack.midterm.model.*;
import com.ironhack.midterm.repository.CheckingRepository;
import com.ironhack.midterm.repository.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class CheckingService {
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private AccountHolderService accountHolderService;

    public List<Checking> findAll() {
        return checkingRepository.findAll();
    }

    public Checking findById(User user, Integer id) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        Checking checking = checkingRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
        if (!user.canAccess(checking)) throw new ForbiddenAccessException();
        return checking;
    }

    public Money findBalance(User user, Integer id) {
        return findById(user, id).getBalance();
    }

    public Checking create(AccountInstance accountInstance, Integer secondaryOwnerId) {
        AccountHolder primaryOwner = accountHolderService.findById(accountInstance.getPrimaryOwnerId());
        AccountHolder secondaryOwner = null;
        if (secondaryOwnerId!=null)
            secondaryOwner = accountHolderService.findById(secondaryOwnerId);

        LocalDate today = LocalDate.now();
        if (today.minusYears(24).isBefore(primaryOwner.getDateOfBirth())) {
            StudentChecking studentChecking = new StudentChecking(accountInstance.getBalance(), accountInstance.getSecretKey(), primaryOwner);
            if (secondaryOwner != null) studentChecking.setSecondaryOwner(secondaryOwner);
            return studentCheckingRepository.save(studentChecking);
        }

        Checking checking = new Checking(accountInstance.getBalance(), accountInstance.getSecretKey(), primaryOwner);
        if (secondaryOwner != null) checking.setSecondaryOwner(secondaryOwner);
        return checkingRepository.save(checking);
    }

    @Transactional
    public void transfer(User user, Integer accSenderId, Transference transference) {
        Checking accSender = findById(user, accSenderId);
        AccountHolder receiver = accountHolderService.findByName(transference.getOwnerName());
        Checking accReceiver = findById(receiver, transference.getReceiverId());

        if (!user.isLoggedIn()) throw new NotLoggedInException();
        if (!user.canAccess(accSender)) throw new ForbiddenAccessException();

        Money amount = new Money(transference.getAmount(), transference.getCurrency());
        accSender.debit(amount);
        checkingRepository.save(accSender);
        accReceiver.credit(amount);
        checkingRepository.save(accReceiver);
    }

    public void credit(User user, Integer id, Money amount) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        Checking checking = findById(user, id);
        if (!user.canAccess(checking)) throw new ForbiddenAccessException();
        checking.credit(amount);
        checkingRepository.save(checking);
    }

    public void debit (User user, Integer id, Money amount) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        Checking checking = findById(user, id);
        if (!user.canAccess(checking)) throw new ForbiddenAccessException();
        checking.debit(amount);
        checkingRepository.save(checking);
    }
}
