package com.ironhack.midterm.repository;

import com.ironhack.midterm.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByUser_Id(Long id);
}
