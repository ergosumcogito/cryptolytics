package com.backendproject.cryptolytics.infrastructure.adapter.persistence;

import com.backendproject.cryptolytics.domain.model.entities.CurrencyIndicator;
import com.backendproject.cryptolytics.domain.model.entities.Currency;
import com.backendproject.cryptolytics.domain.model.entities.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyIndicatorRepository extends JpaRepository<CurrencyIndicator, Long> {
    Optional<CurrencyIndicator> findByCurrencyAndIndicator(Currency currency, Indicator indicator);
}
