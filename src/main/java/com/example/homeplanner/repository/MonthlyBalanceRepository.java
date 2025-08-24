package com.example.homeplanner.repository;

import com.example.homeplanner.entity.MonthlyBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MonthlyBalanceRepository extends JpaRepository<MonthlyBalance, UUID> {
    Optional<MonthlyBalance> findByMonth(String month);
}
