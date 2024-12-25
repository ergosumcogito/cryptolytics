package com.backendproject.cryptolytics.infrastructure.adapter.persistence;

import com.backendproject.cryptolytics.domain.model.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findBySymbol(String symbol);
}
