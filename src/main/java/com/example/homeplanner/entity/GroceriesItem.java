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
    @JoinColumn(name = "groceries_id", nullable = false)
    private GroceriesPurchase groceriesPurchase;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    private BigDecimal qty;

    private String unit;

    private BigDecimal price;

    @Column(name = "line_total", insertable = false, updatable = false)
    private BigDecimal lineTotal;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
