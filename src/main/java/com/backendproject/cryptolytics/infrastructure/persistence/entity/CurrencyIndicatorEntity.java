package com.backendproject.cryptolytics.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Currency_Indicator")
public class CurrencyIndicatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CurrencyEntity currency;

    @ManyToOne
    private IndicatorEntity indicator;

    public CurrencyIndicatorEntity(Long id, CurrencyEntity currency, IndicatorEntity indicator) {
        this.id = id;
        this.currency = currency;
        this.indicator = indicator;
    }

    public void setCurrencyEntity(CurrencyEntity currencyEntity) {
        this.currency = currencyEntity;
    }

    public void setIndicatorEntity(IndicatorEntity indicatorEntity) {
        this.indicator = indicatorEntity;
    }
}