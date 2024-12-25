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
    private IndicatorTrackedCurrency indicatorTrackedCurrency;

    private LocalDateTime timestamp;
    private BigDecimal value;

    public IndicatorTrackedCurrency getIndicatorTrackedCurrency() {
        return indicatorTrackedCurrency;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getValue() {
        return value;
    }
}
