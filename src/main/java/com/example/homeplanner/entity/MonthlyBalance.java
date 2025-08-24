package com.example.homeplanner.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "monthly_balance")
public class MonthlyBalance {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(length = 7, nullable = false, unique = true)
    private String month;

    @Column(name = "balance_left", nullable = false, precision = 12, scale = 2)
    private BigDecimal balanceLeft;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
