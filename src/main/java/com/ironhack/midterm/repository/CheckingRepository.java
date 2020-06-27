package com.ironhack.midterm.repository;

import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Integer> {
    Checking findByIdAndPrimaryOwner(Integer accountId, User user);
    Checking findByIdAndSecondaryOwner(Integer accountId, User user);
}
