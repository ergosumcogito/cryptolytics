package com.backendproject.cryptolytics.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CurrencyIndicator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Indicator indicator;

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }
}