package com.example.homeplanner.repository;

import com.example.homeplanner.entity.GroceriesItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroceriesItemRepository extends JpaRepository<GroceriesItem, UUID> {
}
