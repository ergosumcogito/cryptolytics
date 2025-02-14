package com.backendproject.cryptolytics.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Currency_Indicator_Value")
public class CurrencyIndicatorValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CurrencyIndicatorEntity currencyIndicator;

    private BigDecimal value;
    private LocalDateTime timestamp;

    public CurrencyIndicatorValueEntity(Long id, CurrencyIndicatorEntity currencyIndicatorEntity, BigDecimal value, LocalDateTime timestamp) {
        this.id = id;
        this.currencyIndicator = currencyIndicatorEntity;
        this.value = value;
        this.timestamp = timestamp;
    }

    public void setCurrencyIndicatorEntity(CurrencyIndicatorEntity currencyIndicatorEntity) {
        this.currencyIndicator = currencyIndicatorEntity;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}