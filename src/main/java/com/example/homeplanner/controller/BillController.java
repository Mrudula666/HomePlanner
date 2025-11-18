package com.example.homeplanner.controller;

import com.example.homeplanner.entity.Bill;
import com.example.homeplanner.repository.BillRepository;
import com.example.homeplanner.service.ReceiptStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bills")
@Tag(name = "Bills")
public class BillController {

    private static final Logger log = LoggerFactory.getLogger(BillController.class);
    private final BillRepository repository;
    private final ReceiptStorageService storageService;

    public BillController(BillRepository repository, ReceiptStorageService storageService) {
        this.repository = repository;
        this.storageService = storageService;
    }

    @GetMapping
    @Operation(summary = "List bills, optionally filtered by month (YYYY-MM)")
    public List<Bill> all(@RequestParam(value = "month", required = false) String month) {
        if (month == null) {
            log.info("Listing all bills");
            return repository.findAll();
        }
        log.info("Listing bills for month {}", month);
        return repository.findByMonth(month);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create bill with optional receipt upload")
    public ResponseEntity<Bill> create(@RequestPart("bill") Bill bill,
                                       @RequestPart(value = "receipt", required = false) MultipartFile receipt) throws IOException {
        log.info("Creating bill for month {}", bill.getMonth());
        if (receipt != null && !receipt.isEmpty()) {
            bill.setReceiptPath(storageService.store(receipt));
        }
        Bill saved = repository.save(bill);
        return ResponseEntity.ok(saved);
    }

    @PostMapping(value = "/{id}/receipt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Attach or replace bill receipt")
    public ResponseEntity<Bill> uploadReceipt(@PathVariable UUID id,
                                              @RequestPart("receipt") MultipartFile receipt) throws IOException {
        Bill bill = repository.findById(id).orElseThrow();
        bill.setReceiptPath(storageService.store(receipt));
        Bill saved = repository.save(bill);
        return ResponseEntity.ok(saved);
    }
}

