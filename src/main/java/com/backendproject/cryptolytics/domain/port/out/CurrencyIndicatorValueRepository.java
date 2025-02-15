package com.backendproject.cryptolytics.domain.port.out;

import com.backendproject.cryptolytics.domain.model.CurrencyIndicator;
import com.backendproject.cryptolytics.domain.model.CurrencyIndicatorValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CurrencyIndicatorValueRepository {
    List<CurrencyIndicatorValue> findByCurrencyIndicator(CurrencyIndicator currencyIndicator);
    Optional<CurrencyIndicatorValue> findTopByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicator currencyIndicator);
    CurrencyIndicatorValue findFirstByOrderByTimestampAsc();
    Page<CurrencyIndicatorValue> findByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicator currencyIndicator, Pageable pageable);
    Page<CurrencyIndicatorValue> findByCurrencyIndicatorAndTimestampBetween(CurrencyIndicator currencyIndicator, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
