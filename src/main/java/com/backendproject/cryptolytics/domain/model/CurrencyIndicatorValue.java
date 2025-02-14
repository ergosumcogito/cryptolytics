package com.backendproject.cryptolytics.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CurrencyIndicatorValue {
    private Long id;
    private CurrencyIndicator currencyIndicator;
    private BigDecimal value;
    private LocalDateTime timestamp;
}
