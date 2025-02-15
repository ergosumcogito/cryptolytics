package com.backendproject.cryptolytics.infrastructure.persistence.repository;

import com.backendproject.cryptolytics.infrastructure.persistence.entity.CurrencyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CurrencyEntityRepository extends JpaRepository<CurrencyEntity, Long> {
    Optional<CurrencyEntity> findBySymbol(String symbol);
    Page<CurrencyEntity> findAll(Pageable pageable);
}
