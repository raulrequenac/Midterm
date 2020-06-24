package com.ironhack.midterm.repository;

import com.ironhack.midterm.model.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Integer> {
}
