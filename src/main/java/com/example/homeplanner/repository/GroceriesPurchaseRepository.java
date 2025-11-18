package com.example.homeplanner.repository;

import com.example.homeplanner.entity.GroceriesPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface GroceriesPurchaseRepository extends JpaRepository<GroceriesPurchase, UUID> {
    List<GroceriesPurchase> findByDateShoppedBetween(LocalDate start, LocalDate end);
}
