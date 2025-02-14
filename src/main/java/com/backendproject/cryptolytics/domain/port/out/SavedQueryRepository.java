package com.backendproject.cryptolytics.domain.port.out;

import com.backendproject.cryptolytics.domain.model.ApiKey;
import com.backendproject.cryptolytics.domain.model.SavedQuery;
import java.util.List;
import java.util.Optional;

public interface SavedQueryRepository {
    Optional<SavedQuery> findByQueryNameAndApiKey(String queryName, ApiKey apiKey);
    List<SavedQuery> findAllByApiKey(ApiKey apiKey);
    boolean existsByApiKeyAndQueryName(ApiKey apiKey, String queryName);
    void save(SavedQuery savedQuery);
    void delete(SavedQuery savedQuery);
    void deleteAll(List<SavedQuery> savedQueries);
}
