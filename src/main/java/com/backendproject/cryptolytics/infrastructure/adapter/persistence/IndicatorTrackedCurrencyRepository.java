package com.backendproject.cryptolytics.infrastructure.adapter.persistence;

import com.backendproject.cryptolytics.domain.model.entities.Indicator;
import com.backendproject.cryptolytics.domain.model.entities.IndicatorTrackedCurrency;
import com.backendproject.cryptolytics.domain.model.entities.TrackedCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndicatorTrackedCurrencyRepository extends JpaRepository <IndicatorTrackedCurrency, Long> {
    Optional<IndicatorTrackedCurrency> findByIndicatorAndTrackedCurrency(Indicator indicator, TrackedCurrency currency);
}
