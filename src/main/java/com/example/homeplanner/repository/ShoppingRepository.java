package com.example.homeplanner.repository;

import com.example.homeplanner.entity.Shopping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShoppingRepository extends JpaRepository<Shopping, UUID> {
}
