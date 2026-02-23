package com.aditya.stockapp.dto;

import lombok.Data;

@Data
public class WatchlistRequest {
    private String username;
    private String stockId;
}