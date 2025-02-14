package com.backendproject.cryptolytics.infrastructure.persistence.repository;

import com.backendproject.cryptolytics.infrastructure.persistence.entity.IndicatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndicatorEntityRepository extends JpaRepository<IndicatorEntity, Long> {
    Optional<IndicatorEntity> findByName(String name);
}
