package com.example.homeplanner.controller;

import com.example.homeplanner.entity.Shopping;
import com.example.homeplanner.entity.ShoppingItem;
import com.example.homeplanner.repository.ShoppingItemRepository;
import com.example.homeplanner.repository.ShoppingRepository;
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
@RequestMapping("/api/shopping")
@Tag(name = "Shopping")
public class ShoppingController {

    private static final Logger log = LoggerFactory.getLogger(ShoppingController.class);
    private final ShoppingRepository shoppingRepository;
    private final ShoppingItemRepository itemRepository;
    private final ReceiptStorageService storageService;

    public ShoppingController(ShoppingRepository shoppingRepository,
                              ShoppingItemRepository itemRepository,
                              ReceiptStorageService storageService) {
        this.shoppingRepository = shoppingRepository;
        this.itemRepository = itemRepository;
        this.storageService = storageService;
    }

    @GetMapping
    @Operation(summary = "List shopping trips, optionally filtered by month (YYYY-MM)")
    public List<Shopping> all(@RequestParam(value = "month", required = false) String month) {
        if (month == null) {
            log.info("Listing all shopping trips");
            return shoppingRepository.findAll();
        }
        LocalDate start = LocalDate.parse(month + "-01");
        LocalDate end = start.plusMonths(1).minusDays(1);
        log.info("Listing shopping trips for month {}", month);
        return shoppingRepository.findByDateShoppedBetween(start, end);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create shopping trip with optional receipt upload")
    public ResponseEntity<Shopping> create(@RequestPart("trip") Shopping trip,
                                           @RequestPart(value = "receipt", required = false) MultipartFile receipt) throws IOException {
        if (receipt != null && !receipt.isEmpty()) {
            trip.setReceiptPath(storageService.store(receipt));
        }
        log.info("Creating shopping trip at {}", trip.getShopName());
        Shopping saved = shoppingRepository.save(trip);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{id}/items")
    @Operation(summary = "Add item to shopping trip")
    public ResponseEntity<ShoppingItem> addItem(@PathVariable UUID id, @RequestBody ShoppingItem item) {
        Shopping trip = shoppingRepository.findById(id).orElseThrow();
        item.setShopping(trip);
        ShoppingItem saved = itemRepository.save(item);
        return ResponseEntity.ok(saved);
    }

    @PostMapping(value = "/{id}/receipt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Attach or replace shopping receipt")
    public ResponseEntity<Shopping> uploadReceipt(@PathVariable UUID id,
                                                  @RequestPart("receipt") MultipartFile receipt) throws IOException {
        Shopping trip = shoppingRepository.findById(id).orElseThrow();
        trip.setReceiptPath(storageService.store(receipt));
        Shopping saved = shoppingRepository.save(trip);
        return ResponseEntity.ok(saved);
    }
}

