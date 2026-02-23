package com.aditya.stockapp.service;

import com.aditya.stockapp.dto.StockDTO;
import com.aditya.stockapp.entity.Stock;
import com.aditya.stockapp.entity.Watchlist;
import com.aditya.stockapp.repository.StockRepository;
import com.aditya.stockapp.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private WatchlistRepository watchlistRepository;

    public Map<String, Object> getExploreStocks(String username, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Stock> stockPage = stockRepository.findAll(pageable);

        List<StockDTO> dtoList = stockPage.getContent().stream().map(stock -> {
            boolean isFav = watchlistRepository.existsByUsernameAndStockId(username, stock.getId());
            return new StockDTO(stock.getId(), stock.getStockName(), stock.getSymbol(), stock.getBasePrice(), isFav);
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("stocks", dtoList);
        response.put("totalPages", stockPage.getTotalPages());
        return response;
    }

    public Map<String, Object> getMyWatchlist(String username, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Watchlist> watchlistPage = watchlistRepository.findByUsername(username, pageable);

        List<StockDTO> dtoList = watchlistPage.getContent().stream().map(fav -> {
            Stock stock = stockRepository.findById(fav.getStockId()).orElse(new Stock());
            return new StockDTO(stock.getId(), stock.getStockName(), stock.getSymbol(), stock.getBasePrice(), true);
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("stocks", dtoList);
        response.put("totalPages", watchlistPage.getTotalPages());
        return response;
    }

    public void addStockToWatchlist(String username, String stockId) {
        if (!watchlistRepository.existsByUsernameAndStockId(username, stockId)) {
            Watchlist item = new Watchlist();
            item.setUsername(username);
            item.setStockId(stockId);
            watchlistRepository.save(item);
        }
    }

    public void removeStockFromWatchlist(String username, String stockId) {
        watchlistRepository.deleteByUsernameAndStockId(username, stockId);
    }
}