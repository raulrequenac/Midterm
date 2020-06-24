package com.ironhack.midterm.service;

import com.ironhack.midterm.exceptions.ForbiddenAccessException;
import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.exceptions.NotLoggedInException;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.StudentChecking;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.repository.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCheckingService {
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private AccountHolderService accountHolderService;

    public StudentChecking findById(User user, Integer id) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        StudentChecking studentChecking = studentCheckingRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
        if (!user.canAccess(studentChecking)) throw new ForbiddenAccessException();
        return studentChecking;
    }

    public Money findBalance(User user, Integer id) {
        return findById(user, id).getBalance();
    }

    public void credit(User user, Integer id, Money amount) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        StudentChecking studentChecking = findById(user, id);
        if (!user.canAccess(studentChecking)) throw new ForbiddenAccessException();
        studentChecking.credit(amount);
        studentCheckingRepository.save(studentChecking);
    }

    public void debit (User user, Integer id, Money amount) {
        if (!user.isLoggedIn()) throw new NotLoggedInException();
        StudentChecking studentChecking = findById(user, id);
        if (!user.canAccess(studentChecking)) throw new ForbiddenAccessException();
        studentChecking.debit(amount);
        studentCheckingRepository.save(studentChecking);
    }
}
