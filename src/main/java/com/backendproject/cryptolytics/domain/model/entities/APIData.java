package com.backendproject.cryptolytics.domain.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class APIData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "indicator_currency_id", nullable = false)
    private IndicatorTrackedCurrency indicatorCurrency;

    private LocalDateTime createdAt;
    private BigDecimal value;

    public IndicatorTrackedCurrency getIndicatorTrackedCurrency() {
        return indicatorCurrency;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BigDecimal getValue() {
        return value;
    }
}
