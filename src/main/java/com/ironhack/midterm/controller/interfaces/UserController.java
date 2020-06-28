package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.controller.dto.AccountHolderInstance;
import com.ironhack.midterm.model.AccountHolder;
import com.ironhack.midterm.model.Admin;
import com.ironhack.midterm.model.ThirdParty;
import com.ironhack.midterm.model.User;

public interface UserController {
    User login(User user);
    User logout(User user);
    AccountHolder createAccountHolder(AccountHolderInstance accountHolderInstance);
    ThirdParty createThirdParty(ThirdParty thirdParty);
    Admin createAdmin(Admin admin);
}
