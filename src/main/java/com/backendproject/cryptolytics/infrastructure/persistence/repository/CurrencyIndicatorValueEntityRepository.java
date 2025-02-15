package com.backendproject.cryptolytics.infrastructure.persistence.repository;

import com.backendproject.cryptolytics.infrastructure.persistence.entity.CurrencyIndicatorEntity;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.CurrencyIndicatorValueEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CurrencyIndicatorValueEntityRepository extends JpaRepository<CurrencyIndicatorValueEntity, Long> {
    List<CurrencyIndicatorValueEntity> findByCurrencyIndicator(CurrencyIndicatorEntity currencyIndicatorEntity);
    Optional<CurrencyIndicatorValueEntity> findTopByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicatorEntity currencyIndicatorEntity);
    CurrencyIndicatorValueEntity findFirstByOrderByTimestampAsc();
    Page<CurrencyIndicatorValueEntity> findByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicatorEntity currencyIndicatorEntity, Pageable pageable);
    Page<CurrencyIndicatorValueEntity> findByCurrencyIndicatorAndTimestampBetween(CurrencyIndicatorEntity currencyIndicatorEntity, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
