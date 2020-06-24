package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.StudentChecking;
import com.ironhack.midterm.model.User;

import java.math.BigDecimal;

public interface StudentCheckingController {
    StudentChecking findById(User user, Integer id);
    Money findBalance(User user, Integer id);
    void credit(User user, Integer id, Money amount);
    void debit(User user, Integer id, Money amount);
}
