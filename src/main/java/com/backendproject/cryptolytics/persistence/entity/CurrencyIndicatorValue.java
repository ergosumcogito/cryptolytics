package com.backendproject.cryptolytics.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CurrencyIndicatorValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CurrencyIndicator currencyIndicator;

    private BigDecimal value;
    private LocalDateTime timestamp;

    // Getters and setters
}