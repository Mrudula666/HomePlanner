package com.example.homeplanner.controller;

import com.example.homeplanner.entity.OtherIncome;
import com.example.homeplanner.repository.OtherIncomeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/other-income")
@Tag(name = "Other Income")
public class OtherIncomeController {

    private static final Logger log = LoggerFactory.getLogger(OtherIncomeController.class);
    private final OtherIncomeRepository repository;

    public OtherIncomeController(OtherIncomeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "List other income records, optionally filtered by month (YYYY-MM)")
    public List<OtherIncome> all(@RequestParam(value = "month", required = false) String month) {
        if (month == null) {
            log.info("Listing all other income records");
            return repository.findAll();
        }
        LocalDate start = LocalDate.parse(month + "-01");
        LocalDate end = start.plusMonths(1).minusDays(1);
        log.info("Listing other income records for month {}", month);
        return repository.findByReceivedDateBetween(start, end);
    }

    @PostMapping
    @Operation(summary = "Create an other income record")
    public ResponseEntity<OtherIncome> create(@RequestBody OtherIncome income) {
        log.info("Creating other income from source {}", income.getSource());
        OtherIncome saved = repository.save(income);
        return ResponseEntity.ok(saved);
    }
}

