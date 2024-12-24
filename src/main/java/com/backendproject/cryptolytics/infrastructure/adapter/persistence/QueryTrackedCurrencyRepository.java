package com.backendproject.cryptolytics.infrastructure.adapter.persistence;

import com.backendproject.cryptolytics.domain.model.entities.QueryTrackedCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryTrackedCurrencyRepository extends JpaRepository<QueryTrackedCurrency, Long> {
}
