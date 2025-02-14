package com.backendproject.cryptolytics.infrastructure.persistence.repository;

import com.backendproject.cryptolytics.infrastructure.persistence.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    boolean existsByApiKey(String apiKey);
    void deleteByApiKey(String apiKey);
    Optional<ApiKey> findByApiKey(String apiKey);
}
