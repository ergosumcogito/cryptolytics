package com.backendproject.cryptolytics.persistence.repository;

import com.backendproject.cryptolytics.persistence.entity.CurrencyIndicator;
import com.backendproject.cryptolytics.persistence.entity.Currency;
import com.backendproject.cryptolytics.persistence.entity.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyIndicatorRepository extends JpaRepository<CurrencyIndicator, Long> {
    Optional<CurrencyIndicator> findByCurrencyAndIndicator(Currency currency, Indicator indicator);
}
