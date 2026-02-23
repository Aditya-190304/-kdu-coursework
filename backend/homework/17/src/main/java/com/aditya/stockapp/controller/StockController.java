package com.aditya.stockapp.controller;

import com.aditya.stockapp.dto.WatchlistRequest;
import com.aditya.stockapp.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173"})
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stocks")
    public ResponseEntity<Map<String, Object>> getStocks(
            @RequestParam String username,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(stockService.getExploreStocks(username, page, limit));
    }

    @GetMapping("/watchlist")
    public ResponseEntity<Map<String, Object>> getWatchlist(
            @RequestParam String username,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(stockService.getMyWatchlist(username, page, limit));
    }

    @PostMapping("/watchlist/add")
    public ResponseEntity<?> addToWatchlist(@RequestBody WatchlistRequest request) {
        stockService.addStockToWatchlist(request.getUsername(), request.getStockId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/watchlist/remove")
    public ResponseEntity<?> removeFromWatchlist(@RequestBody WatchlistRequest request) {
        stockService.removeStockFromWatchlist(request.getUsername(), request.getStockId());
        return ResponseEntity.ok().build();
    }
}