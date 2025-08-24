package com.example.homeplanner.controller;

import com.example.homeplanner.entity.GroceriesItem;
import com.example.homeplanner.entity.GroceriesPurchase;
import com.example.homeplanner.repository.GroceriesItemRepository;
import com.example.homeplanner.repository.GroceriesPurchaseRepository;
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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/groceries")
@Tag(name = "Groceries")
public class GroceriesPurchaseController {

    private static final Logger log = LoggerFactory.getLogger(GroceriesPurchaseController.class);
    private final GroceriesPurchaseRepository purchaseRepository;
    private final GroceriesItemRepository itemRepository;
    private final ReceiptStorageService storageService;

    public GroceriesPurchaseController(GroceriesPurchaseRepository purchaseRepository,
                                       GroceriesItemRepository itemRepository,
                                       ReceiptStorageService storageService) {
        this.purchaseRepository = purchaseRepository;
        this.itemRepository = itemRepository;
        this.storageService = storageService;
    }

    @GetMapping
    @Operation(summary = "List groceries purchases, optionally filtered by month (YYYY-MM)")
    public List<GroceriesPurchase> all(@RequestParam(value = "month", required = false) String month) {
        if (month == null) {
            log.info("Listing all groceries purchases");
            return purchaseRepository.findAll();
        }
        LocalDate start = LocalDate.parse(month + "-01");
        LocalDate end = start.plusMonths(1).minusDays(1);
        log.info("Listing groceries purchases for month {}", month);
        return purchaseRepository.findByDateShoppedBetween(start, end);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create groceries purchase with optional receipt upload")
    public ResponseEntity<GroceriesPurchase> create(@RequestPart("purchase") GroceriesPurchase purchase,
                                                    @RequestPart(value = "receipt", required = false) MultipartFile receipt) throws IOException {
        if (receipt != null && !receipt.isEmpty()) {
            purchase.setReceiptPath(storageService.store(receipt));
        }
        log.info("Creating groceries purchase at {}", purchase.getShopName());
        GroceriesPurchase saved = purchaseRepository.save(purchase);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{id}/items")
    @Operation(summary = "Add item to groceries purchase")
    public ResponseEntity<GroceriesItem> addItem(@PathVariable UUID id, @RequestBody GroceriesItem item) {
        GroceriesPurchase purchase = purchaseRepository.findById(id).orElseThrow();
        item.setGroceriesPurchase(purchase);
        GroceriesItem saved = itemRepository.save(item);
        return ResponseEntity.ok(saved);
    }

    @PostMapping(value = "/{id}/receipt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Attach or replace groceries receipt")
    public ResponseEntity<GroceriesPurchase> uploadReceipt(@PathVariable UUID id,
                                                           @RequestPart("receipt") MultipartFile receipt) throws IOException {
        GroceriesPurchase purchase = purchaseRepository.findById(id).orElseThrow();
        purchase.setReceiptPath(storageService.store(receipt));
        GroceriesPurchase saved = purchaseRepository.save(purchase);
        return ResponseEntity.ok(saved);
    }
}

