package com.ironhack.midterm.service;

import com.ironhack.midterm.model.ThirdParty;
import com.ironhack.midterm.repository.ThirdPartyRepository;
import com.ironhack.midterm.util.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyService {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    public ThirdParty create(ThirdParty thirdParty) {
        thirdParty.setPassword(PasswordUtility.encryptPassword(thirdParty.getPassword()));
        return thirdPartyRepository.save(thirdParty);
    }
}
