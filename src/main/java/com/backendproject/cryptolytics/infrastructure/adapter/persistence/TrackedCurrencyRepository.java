package com.backendproject.cryptolytics.infrastructure.adapter.persistence;

import com.backendproject.cryptolytics.domain.model.entities.TrackedCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TrackedCurrencyRepository extends JpaRepository<TrackedCurrency, Long> {
    Optional<TrackedCurrency> findBySymbol(String symbol);
}

