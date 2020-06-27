package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.controller.dto.Transference;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.User;

public interface AccountController {
    Checking findById(User user, Integer id);
    Money findBalance(User user, Integer id);
    void credit(User user, Integer id, Money amount, String hashedKey);
    void debit(User user, Integer id, Money amount, String hashedKey);
    void unfreeze(User user, Integer id);
    void transfer(User user, Integer id, Transference transference);
}
