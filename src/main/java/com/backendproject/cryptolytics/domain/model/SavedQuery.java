package com.backendproject.cryptolytics.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SavedQuery {
    private Long id;
    private ApiKey apiKey;
    private String queryName;
    private Currency currency;
    private Indicator indicator;

    public SavedQuery(ApiKey apiKey, String queryName, Currency currency, Indicator indicator) {
        this.apiKey = apiKey;
        this.queryName = queryName;
        this.currency = currency;
        this.indicator = indicator;
    }
}
