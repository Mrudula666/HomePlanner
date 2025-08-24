package com.example.homeplanner.service;

import com.example.homeplanner.dto.Forecast;
import com.example.homeplanner.dto.MonthlySnapshot;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ForecastService {

    private final SnapshotService snapshotService;

    public ForecastService(SnapshotService snapshotService) {
        this.snapshotService = snapshotService;
    }

    public Forecast getForecast(String month) {
        MonthlySnapshot snapshot = snapshotService.getSnapshot(month);
        BigDecimal projected = snapshot.getNet();
        return new Forecast(month, projected);
    }
}

