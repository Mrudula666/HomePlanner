package com.example.homeplanner.repository;

import com.example.homeplanner.entity.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, UUID> {
}
