package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.ThirdParty;

public interface UserController {
    AccountHolder createAccountHolder(AccountHolder accountHolder);
    ThirdParty createThirdParty(ThirdParty thirdParty);
}
