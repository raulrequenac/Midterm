package com.ironhack.midterm.service;

import com.ironhack.midterm.exceptions.IdNotFoundException;
import com.ironhack.midterm.model.*;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.AdminRepository;
import com.ironhack.midterm.repository.ThirdPartyRepository;
import com.ironhack.midterm.repository.UserRepository;
import com.ironhack.midterm.security.CustomSecurityUser;
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

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Invalid username/password combination.");
        return new CustomSecurityUser(user);
    }

    public User login(User user) {
        user.logIn();
        return saveUser(user);
    }

    public User logout(User user) {
        user.logOut();
        return saveUser(user);
    }

    public User saveUser(User user) {
        if (user instanceof AccountHolder) return accountHolderRepository.save((AccountHolder)user);
        if (user instanceof ThirdParty) return thirdPartyRepository.save((ThirdParty)user);
        else return adminRepository.save((Admin) user);
    }
}
