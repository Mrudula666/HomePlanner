package com.example.homeplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Forecast {
    private String month;
    private BigDecimal projectedBalance;
}

