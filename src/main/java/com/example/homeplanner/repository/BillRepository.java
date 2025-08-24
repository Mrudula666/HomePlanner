package com.example.homeplanner.repository;

import com.example.homeplanner.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {
    List<Bill> findByMonth(String month);
}
