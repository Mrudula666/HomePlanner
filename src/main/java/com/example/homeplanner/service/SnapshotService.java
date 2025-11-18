package com.example.homeplanner.service;

import com.example.homeplanner.dto.MonthlySnapshot;
import com.example.homeplanner.repository.BillRepository;
import com.example.homeplanner.repository.GroceriesPurchaseRepository;
import com.example.homeplanner.repository.MonthlyBalanceRepository;
import com.example.homeplanner.repository.MonthlyIncomeRepository;
import com.example.homeplanner.repository.OtherIncomeRepository;
import com.example.homeplanner.repository.ShoppingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class SnapshotService {

    private final MonthlyIncomeRepository monthlyIncomeRepository;
    private final OtherIncomeRepository otherIncomeRepository;
    private final BillRepository billRepository;
    private final GroceriesPurchaseRepository groceriesPurchaseRepository;
    private final ShoppingRepository shoppingRepository;
    private final MonthlyBalanceRepository monthlyBalanceRepository;

    public SnapshotService(MonthlyIncomeRepository monthlyIncomeRepository,
                           OtherIncomeRepository otherIncomeRepository,
                           BillRepository billRepository,
                           GroceriesPurchaseRepository groceriesPurchaseRepository,
                           ShoppingRepository shoppingRepository,
                           MonthlyBalanceRepository monthlyBalanceRepository) {
        this.monthlyIncomeRepository = monthlyIncomeRepository;
        this.otherIncomeRepository = otherIncomeRepository;
        this.billRepository = billRepository;
        this.groceriesPurchaseRepository = groceriesPurchaseRepository;
        this.shoppingRepository = shoppingRepository;
        this.monthlyBalanceRepository = monthlyBalanceRepository;
    }

    public MonthlySnapshot getSnapshot(String month) {
        LocalDate start = LocalDate.parse(month + "-01");
        LocalDate end = start.plusMonths(1).minusDays(1);

        BigDecimal income = monthlyIncomeRepository.findByMonth(month)
                .stream()
                .map(m -> m.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal otherIncome = otherIncomeRepository.findByReceivedDateBetween(start, end)
                .stream()
                .map(o -> o.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal bills = billRepository.findByMonth(month)
                .stream()
                .map(b -> b.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal groceries = groceriesPurchaseRepository.findByDateShoppedBetween(start, end)
                .stream()
                .map(g -> g.getTotalAmount() == null ? BigDecimal.ZERO : g.getTotalAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal shopping = shoppingRepository.findByDateShoppedBetween(start, end)
                .stream()
                .map(s -> s.getTotalAmount() == null ? BigDecimal.ZERO : s.getTotalAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balance = monthlyBalanceRepository.findByMonth(month)
                .map(b -> b.getBalanceLeft())
                .orElse(null);

        BigDecimal net = income.add(otherIncome).subtract(bills).subtract(groceries).subtract(shopping);

        return new MonthlySnapshot(month, income, otherIncome, bills, groceries, shopping, balance, net);
    }
}

