package com.example.homeplanner.controller;

import com.example.homeplanner.dto.MonthlySnapshot;
import com.example.homeplanner.service.SnapshotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/snapshot")
@Tag(name = "Monthly Snapshot")
public class SnapshotController {

    private static final Logger log = LoggerFactory.getLogger(SnapshotController.class);
    private final SnapshotService snapshotService;

    public SnapshotController(SnapshotService snapshotService) {
        this.snapshotService = snapshotService;
    }

    @GetMapping("/{month}")
    @Operation(summary = "View monthly snapshot for a given month (YYYY-MM)")
    public MonthlySnapshot snapshot(@PathVariable String month) {
        log.info("Generating snapshot for month {}", month);
        return snapshotService.getSnapshot(month);
    }
}

