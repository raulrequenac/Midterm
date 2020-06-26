package com.ironhack.midterm.repository;

import com.ironhack.midterm.model.Checking;
import com.ironhack.midterm.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT COUNT(*) FROM transaction WHERE account_id=:accountId AND user_id=:userId AND realized_at>=:startOfDay ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    Integer todayTotal(@Param("accountId") Integer accountId, @Param("userId") Integer userId, @Param("startOfDay") LocalDate startOfDay);
    @Query(value = "SELECT COUNT(*) FROM transaction WHERE account_id=:accountId AND user_id=:userId AND realized_at<:startOfDay GROUP BY realized_at ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    Integer maxTotal(@Param("accountId") Integer accountId, @Param("userId") Integer userId, @Param("startOfDay") LocalDate startOfDay);
}
