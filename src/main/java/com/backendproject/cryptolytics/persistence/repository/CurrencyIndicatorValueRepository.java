package com.backendproject.cryptolytics.persistence.repository;

import com.backendproject.cryptolytics.persistence.entity.CurrencyIndicator;
import com.backendproject.cryptolytics.persistence.entity.CurrencyIndicatorValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CurrencyIndicatorValueRepository extends JpaRepository<CurrencyIndicatorValue, Long> {
    List<CurrencyIndicatorValue> findByCurrencyIndicator(CurrencyIndicator currencyIndicator);
    Optional<CurrencyIndicatorValue> findTopByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicator currencyIndicator);
    CurrencyIndicatorValue findFirstByOrderByTimestampAsc();
    List<CurrencyIndicatorValue> findByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicator currencyIndicator);
    List<CurrencyIndicatorValue> findByCurrencyIndicatorAndTimestampBetween(CurrencyIndicator currencyIndicator, LocalDateTime start, LocalDateTime end);
}
