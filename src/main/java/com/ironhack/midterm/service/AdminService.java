package com.ironhack.midterm.service;

import com.ironhack.midterm.MidtermApplication;
import com.ironhack.midterm.model.Admin;
import com.ironhack.midterm.model.Role;
import com.ironhack.midterm.repository.AdminRepository;
import com.ironhack.midterm.repository.RoleRepository;
import com.ironhack.midterm.util.PasswordUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleRepository roleRepository;

    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public Admin create(Admin admin) {
        LOGGER.info("[INIT] - Create Admin User");
        admin.setPassword(PasswordUtility.encryptPassword(admin.getPassword()));
        roleRepository.save(new Role("ROLE_ADMIN", admin));
        Admin a = adminRepository.save(admin);
        LOGGER.info("[END] - Create Admin User");
        return a;
    }
}
