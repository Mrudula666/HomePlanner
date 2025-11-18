package com.example.homeplanner.controller;

import com.example.homeplanner.entity.MonthlyBalance;
import com.example.homeplanner.repository.MonthlyBalanceRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-balance")
@Tag(name = "Monthly Balance")
public class MonthlyBalanceController {

    private static final Logger log = LoggerFactory.getLogger(MonthlyBalanceController.class);
    private final MonthlyBalanceRepository repository;

    public MonthlyBalanceController(MonthlyBalanceRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "List monthly balance records")
    public List<MonthlyBalance> all() {
        log.info("Listing all monthly balances");
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Store monthly balance")
    public ResponseEntity<MonthlyBalance> create(@RequestBody MonthlyBalance balance) {
        log.info("Saving monthly balance for {}", balance.getMonth());
        MonthlyBalance saved = repository.save(balance);
        return ResponseEntity.ok(saved);
    }
}

