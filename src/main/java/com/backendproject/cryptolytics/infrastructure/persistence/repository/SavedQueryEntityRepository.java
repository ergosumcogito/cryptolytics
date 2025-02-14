package com.backendproject.cryptolytics.infrastructure.persistence.repository;

import com.backendproject.cryptolytics.infrastructure.persistence.entity.ApiKeyEntity;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.SavedQueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SavedQueryEntityRepository extends JpaRepository<SavedQueryEntity, Long> {
    Optional<SavedQueryEntity> findByQueryNameAndApiKey(String queryName, ApiKeyEntity apiKeyEntity);
    List<SavedQueryEntity> findAllByApiKey(ApiKeyEntity apiKeyEntity);
    boolean existsByApiKeyAndQueryName(ApiKeyEntity apiKeyEntity, String queryName);
}
