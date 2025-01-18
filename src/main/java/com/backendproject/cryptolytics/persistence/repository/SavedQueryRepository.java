package com.backendproject.cryptolytics.persistence.repository;

import com.backendproject.cryptolytics.persistence.entity.ApiKey;
import com.backendproject.cryptolytics.persistence.entity.SavedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavedQueryRepository extends JpaRepository<SavedQuery, Long> {
    Optional<SavedQuery> findByQueryNameAndApiKey(String queryName, ApiKey apiKey);
}
