package com.backendproject.cryptolytics.infrastructure.adapter.persistence;

import com.backendproject.cryptolytics.domain.model.entities.APIData;
import com.backendproject.cryptolytics.domain.model.entities.IndicatorTrackedCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface APIDataRepository extends JpaRepository<APIData, Long> {
    Optional<APIData> findByIndicatorTrackedCurrencyAndTimestamp(IndicatorTrackedCurrency indicatorTrackedCurrency, LocalDateTime timestamp);
}
