package com.backendproject.cryptolytics.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Currency {
    private final Long id;
    private final String symbol;
    private final String name;
}
