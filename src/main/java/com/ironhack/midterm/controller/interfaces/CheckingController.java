package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.controller.dto.AccountInstance;
import com.ironhack.midterm.controller.dto.Transference;
import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.User;

public interface CheckingController {
    Checking create(AccountInstance accountInstance, Integer secondaryOwnerId);
}
