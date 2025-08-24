package com.example.homeplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MonthlySnapshot {
    private String month;
    private BigDecimal totalIncome;
    private BigDecimal totalOtherIncome;
    private BigDecimal totalBills;
    private BigDecimal totalGroceries;
    private BigDecimal totalShopping;
    private BigDecimal balanceLeft;
    private BigDecimal net;
}

