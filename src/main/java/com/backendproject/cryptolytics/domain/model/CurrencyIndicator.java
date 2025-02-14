package com.backendproject.cryptolytics.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CurrencyIndicator {
    private Long id;
    private Currency currency;
    private Indicator indicator;
}