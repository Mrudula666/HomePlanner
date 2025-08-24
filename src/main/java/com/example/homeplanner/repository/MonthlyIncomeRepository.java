package com.example.homeplanner.repository;

import com.example.homeplanner.entity.MonthlyIncome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MonthlyIncomeRepository extends JpaRepository<MonthlyIncome, UUID> {
}
