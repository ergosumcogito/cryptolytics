package com.backendproject.cryptolytics.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
public class CurrencyIndicatorValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CurrencyIndicator currencyIndicator;

    private BigDecimal value;
    private LocalDateTime timestamp;

    public void setCurrencyIndicator(CurrencyIndicator currencyIndicator) {
        this.currencyIndicator = currencyIndicator;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}