package com.example.homeplanner.repository;

import com.example.homeplanner.entity.OtherIncome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OtherIncomeRepository extends JpaRepository<OtherIncome, UUID> {
    List<OtherIncome> findByReceivedDateBetween(LocalDate start, LocalDate end);
}
