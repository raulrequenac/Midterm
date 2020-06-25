package com.ironhack.midterm.repository;

import com.ironhack.midterm.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
