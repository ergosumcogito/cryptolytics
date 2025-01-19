package com.backendproject.cryptolytics.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SavedQueryDTO {
    private String queryName;
    private String currency;
    private String indicator;

    public SavedQueryDTO(String queryName, String currency, String indicator) {
        this.queryName = queryName;
        this.currency = currency;
        this.indicator = indicator;
    }
}
