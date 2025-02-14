package com.backendproject.cryptolytics.domain.port.out;

import com.backendproject.cryptolytics.domain.model.CurrencyIndicator;
import com.backendproject.cryptolytics.domain.model.CurrencyIndicatorValue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CurrencyIndicatorValueRepository {
    List<CurrencyIndicatorValue> findByCurrencyIndicator(CurrencyIndicator currencyIndicator);
    Optional<CurrencyIndicatorValue> findTopByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicator currencyIndicator);
    CurrencyIndicatorValue findFirstByOrderByTimestampAsc();
    List<CurrencyIndicatorValue> findByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicator currencyIndicator);
    List<CurrencyIndicatorValue> findByCurrencyIndicatorAndTimestampBetween(CurrencyIndicator currencyIndicator, LocalDateTime start, LocalDateTime end);
}
