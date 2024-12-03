package com.backendproject.cryptolytics.infrastructure.adapter.persistence;

import com.backendproject.cryptolytics.domain.model.entities.TrackedCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// Mark this as a Spring Repository
@Repository
public interface CurrencyRepository extends JpaRepository<TrackedCurrency, Long> {

    // Optional custom query methods, e.g., finding by symbol
    Optional<TrackedCurrency> findBySymbol(String symbol);
}

