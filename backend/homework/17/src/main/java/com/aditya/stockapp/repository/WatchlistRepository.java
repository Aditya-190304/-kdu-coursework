package com.aditya.stockapp.repository;

import com.aditya.stockapp.entity.Watchlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    boolean existsByUsernameAndStockId(String username, String stockId);
    Page<Watchlist> findByUsername(String username, Pageable pageable);

    @Transactional
    void deleteByUsernameAndStockId(String username, String stockId);
}