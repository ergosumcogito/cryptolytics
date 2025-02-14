package com.backendproject.cryptolytics.infrastructure.persistence.repository;

import com.backendproject.cryptolytics.infrastructure.persistence.entity.CurrencyIndicatorEntity;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.CurrencyIndicatorValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CurrencyIndicatorValueEntityRepository extends JpaRepository<CurrencyIndicatorValueEntity, Long> {
    List<CurrencyIndicatorValueEntity> findByCurrencyIndicator(CurrencyIndicatorEntity currencyIndicatorEntity);
    Optional<CurrencyIndicatorValueEntity> findTopByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicatorEntity currencyIndicatorEntity);
    CurrencyIndicatorValueEntity findFirstByOrderByTimestampAsc();
    List<CurrencyIndicatorValueEntity> findByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicatorEntity currencyIndicatorEntity);
    List<CurrencyIndicatorValueEntity> findByCurrencyIndicatorAndTimestampBetween(CurrencyIndicatorEntity currencyIndicatorEntity, LocalDateTime start, LocalDateTime end);
}
