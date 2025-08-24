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

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "date_shopped", nullable = false)
    private LocalDate dateShopped;

    @Enumerated(EnumType.STRING)
    @Column(name = "cuisine_type", nullable = false)
    private CuisineType cuisineType = CuisineType.INDIAN;

    @Column(name = "receipt_path")
    private String receiptPath;

    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
