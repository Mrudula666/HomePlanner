package com.example.homeplanner.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "groceries_purchase")
public class GroceriesPurchase {

    @Id
    @GeneratedValue
    private UUID id;

    private String shopName;

    private LocalDate dateShopped;

    @Enumerated(EnumType.STRING)
    private CuisineType cuisineType = CuisineType.INDIAN;

    private String receiptPath;

    private BigDecimal totalAmount;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
