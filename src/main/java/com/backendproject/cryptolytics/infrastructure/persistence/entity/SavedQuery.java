package com.backendproject.cryptolytics.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "Saved_Query")
public class SavedQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "api_key_id", nullable = false)
    private ApiKey apiKey;

    private String queryName;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private CurrencyEntity currencyEntity;

    @ManyToOne
    @JoinColumn(name = "indicator_id", nullable = false)
    private IndicatorEntity indicatorEntity;

    public void setApiKey(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public void setCurrencyEntity(CurrencyEntity currencyEntity) {
        this.currencyEntity = currencyEntity;
    }

    public void setIndicatorEntity(IndicatorEntity indicatorEntity) {
        this.indicatorEntity = indicatorEntity;
    }
}
