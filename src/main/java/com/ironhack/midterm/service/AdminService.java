package com.ironhack.midterm.service;

import com.ironhack.midterm.model.Admin;
import com.ironhack.midterm.model.Role;
import com.ironhack.midterm.repository.AdminRepository;
import com.ironhack.midterm.repository.RoleRepository;
import com.ironhack.midterm.util.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Admin create(Admin admin) {
        admin.setPassword(PasswordUtility.encryptPassword(admin.getPassword()));
        roleRepository.save(new Role("ACCOUNT_HOLDER", admin));
        return adminRepository.save(admin);
    }
}
