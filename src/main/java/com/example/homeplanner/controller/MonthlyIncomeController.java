package com.example.homeplanner.controller;

import com.example.homeplanner.entity.MonthlyIncome;
import com.example.homeplanner.repository.MonthlyIncomeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-income")
@Tag(name = "Monthly Income")
public class MonthlyIncomeController {

    private static final Logger log = LoggerFactory.getLogger(MonthlyIncomeController.class);
    private final MonthlyIncomeRepository repository;

    public MonthlyIncomeController(MonthlyIncomeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "List monthly income records")
    public List<MonthlyIncome> all() {
        log.info("Listing all monthly income records");
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a monthly income record")
    public ResponseEntity<MonthlyIncome> create(@RequestBody MonthlyIncome income) {
        log.info("Creating monthly income for month {}", income.getMonth());
        MonthlyIncome saved = repository.save(income);
        return ResponseEntity.ok(saved);
    }
}
