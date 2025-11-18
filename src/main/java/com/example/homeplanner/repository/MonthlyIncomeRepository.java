package com.example.homeplanner.repository;

import com.example.homeplanner.entity.MonthlyIncome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MonthlyIncomeRepository extends JpaRepository<MonthlyIncome, UUID> {
    List<MonthlyIncome> findByMonth(String month);
}
