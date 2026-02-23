package com.aditya.stockapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Stock {
    @Id
    private String id;
    private String stockName;
    private String symbol;
    private Double basePrice;
}