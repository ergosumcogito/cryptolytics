package com.backendproject.cryptolytics.infrastructure.persistence.repository;

import com.backendproject.cryptolytics.infrastructure.persistence.entity.CurrencyIndicatorEntity;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.CurrencyEntity;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.IndicatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyIndicatorEntityRepository extends JpaRepository<CurrencyIndicatorEntity, Long> {
    Optional<CurrencyIndicatorEntity> findByCurrencyAndIndicator(CurrencyEntity currencyEntity, IndicatorEntity indicatorEntity);
}
