package com.backendproject.cryptolytics.infrastructure.persistence.repository;

import com.backendproject.cryptolytics.infrastructure.persistence.entity.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApiKeyEntityRepository extends JpaRepository<ApiKeyEntity, Long> {
    boolean existsByApiKey(String apiKey);
    void deleteByApiKey(String apiKey);
    Optional<ApiKeyEntity> findByApiKey(String apiKey);
}
