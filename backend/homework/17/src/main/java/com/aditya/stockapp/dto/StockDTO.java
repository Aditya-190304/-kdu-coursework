package com.aditya.stockapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockDTO {
    @JsonProperty("_id")
    private String id;

    @JsonProperty("stock_name")
    private String stockName;

    private String symbol;

    @JsonProperty("base_price")
    private Double basePrice;

    @JsonProperty("isFavorite")
    private boolean isFavorite;
}