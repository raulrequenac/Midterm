package com.ironhack.midterm.service;

import com.ironhack.midterm.MidtermApplication;
import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.model.*;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.AdminRepository;
import com.ironhack.midterm.repository.ThirdPartyRepository;
import com.ironhack.midterm.repository.UserRepository;
import com.ironhack.midterm.security.CustomSecurityUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private AdminRepository adminRepository;

    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public User findById(Integer id) {
        LOGGER.info("[INIT] - Find User with id: "+id);
        User user = userRepository.findById(id).orElseThrow(() -> new IdNotFoundException("user", id));
        LOGGER.info("[END] - Find User with id: "+id);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("[INIT] - Load User by username: "+username);
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Invalid username/password combination.");
        UserDetails ud = new CustomSecurityUser(user);
        LOGGER.info("[END] - Load User by username: "+username);
        return ud;
    }

    public User login(User user) {
        LOGGER.info("[INIT] - Log in User: "+user.getName());
        user.logIn();
        User u = saveUser(user);
        LOGGER.info("[END] - Log in User: "+user.getName());
        return u;
    }

    public User logout(User user) {
        LOGGER.info("[INIT] - Log out User: "+user.getName());
        user.logOut();
        User u = saveUser(user);
        LOGGER.info("[END] - Log out User: "+user.getName());
        return u;
    }

    public User saveUser(User user) {
        if (user instanceof AccountHolder) return accountHolderRepository.save((AccountHolder)user);
        if (user instanceof ThirdParty) return thirdPartyRepository.save((ThirdParty)user);
        else return adminRepository.save((Admin) user);
    }
}
