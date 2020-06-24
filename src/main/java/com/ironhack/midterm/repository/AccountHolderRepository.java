package com.ironhack.midterm.repository;

import com.ironhack.midterm.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Integer> {
    AccountHolder findByName(String name);
}
