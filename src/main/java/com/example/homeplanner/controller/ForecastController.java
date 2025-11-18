package com.example.homeplanner.controller;

import com.example.homeplanner.dto.Forecast;
import com.example.homeplanner.service.ForecastService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forecast")
@Tag(name = "Forecast")
public class ForecastController {

    private static final Logger log = LoggerFactory.getLogger(ForecastController.class);
    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/{month}")
    @Operation(summary = "View forecast for a given month (YYYY-MM)")
    public Forecast forecast(@PathVariable String month) {
        log.info("Generating forecast for month {}", month);
        return forecastService.getForecast(month);
    }
}

