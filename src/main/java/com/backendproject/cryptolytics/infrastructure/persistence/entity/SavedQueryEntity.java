package com.backendproject.cryptolytics.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "Saved_Query")
public class SavedQueryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "api_key_id", nullable = false)
    private ApiKeyEntity apiKey;

    private String queryName;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private CurrencyEntity currency;

    @ManyToOne
    @JoinColumn(name = "indicator_id", nullable = false)
    private IndicatorEntity indicator;

    public SavedQueryEntity(Long id, ApiKeyEntity apiKeyEntity, String queryName, CurrencyEntity currencyEntity, IndicatorEntity indicatorEntity) {
        this.id = id;
        this.apiKey = apiKeyEntity;
        this.queryName = queryName;
        this.currency = currencyEntity;
        this.indicator = indicatorEntity;
    }

    public void setApiKey(ApiKeyEntity apiKeyEntity) {
        this.apiKey = apiKeyEntity;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public void setCurrency(CurrencyEntity currencyEntity) {
        this.currency = currencyEntity;
    }

    public void setIndicator(IndicatorEntity indicatorEntity) {
        this.indicator = indicatorEntity;
    }
}
