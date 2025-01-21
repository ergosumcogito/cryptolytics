package com.backendproject.cryptolytics.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
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
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "indicator_id", nullable = false)
    private Indicator indicator;

    public void setApiKey(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }
}
