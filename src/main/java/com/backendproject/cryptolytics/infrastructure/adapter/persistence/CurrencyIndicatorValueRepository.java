package com.backendproject.cryptolytics.infrastructure.adapter.persistence;

import com.backendproject.cryptolytics.domain.model.entities.CurrencyIndicator;
import com.backendproject.cryptolytics.domain.model.entities.CurrencyIndicatorValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyIndicatorValueRepository extends JpaRepository<CurrencyIndicatorValue, Long> {
    List<CurrencyIndicatorValue> findByCurrencyIndicator(CurrencyIndicator currencyIndicator);
    Optional<CurrencyIndicatorValue> findTopByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicator currencyIndicator);
    CurrencyIndicatorValue findFirstByOrderByTimestampAsc();
}
