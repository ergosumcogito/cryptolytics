package com.backendproject.cryptolytics.infrastructure.persistence.repository;

import com.backendproject.cryptolytics.infrastructure.persistence.entity.ApiKey;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.SavedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedQueryRepository extends JpaRepository<SavedQuery, Long> {
    Optional<SavedQuery> findByQueryNameAndApiKey(String queryName, ApiKey apiKey);
    List<SavedQuery> findAllByApiKey(ApiKey apiKey);
    boolean existsByApiKeyAndQueryName(ApiKey apiKey, String queryName);
}
