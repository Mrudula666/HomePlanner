package com.example.homeplanner.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "groceries_item")
public class GroceriesItem {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groceries_id")
    private GroceriesPurchase groceriesPurchase;

    private String itemName;

    private BigDecimal qty;

    private String unit;

    private BigDecimal price;

    @Column(insertable = false, updatable = false)
    private BigDecimal lineTotal;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
