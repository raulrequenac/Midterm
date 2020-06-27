package com.ironhack.midterm.service;

import com.ironhack.midterm.MidtermApplication;
import com.ironhack.midterm.model.Role;
import com.ironhack.midterm.model.ThirdParty;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.repository.RoleRepository;
import com.ironhack.midterm.repository.ThirdPartyRepository;
import com.ironhack.midterm.util.PasswordUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyService {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private RoleRepository roleRepository;

    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public boolean correctHashedKey(User user, String hashedKey) {
        LOGGER.info("[INIT] - Is User: "+user.getName()+" hashkey correct");
        if (hashedKey==null) return false;
        boolean isCorrect = thirdPartyRepository.findByIdAndHashedKey(user.getId(), hashedKey) != null;
        LOGGER.info("[END] - Is User: "+user.getName()+" hashkey correct");
        return isCorrect;
    }

    public ThirdParty create(ThirdParty thirdParty) {
        LOGGER.info("[INIT] - Create ThirdPart Account");
        thirdParty.setPassword(PasswordUtility.encryptPassword(thirdParty.getPassword()));
        thirdParty.setHashedKey(PasswordUtility.encryptPassword(thirdParty.getHashedKey()));
        roleRepository.save(new Role("ROLE_THIRD_PARTY", thirdParty));
        ThirdParty tp = thirdPartyRepository.save(thirdParty);
        LOGGER.info("[END] - Create ThirdPart Account");
        return tp;
    }
}
