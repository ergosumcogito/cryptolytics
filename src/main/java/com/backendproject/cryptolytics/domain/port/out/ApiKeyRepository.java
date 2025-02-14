package com.backendproject.cryptolytics.domain.port.out;

import com.backendproject.cryptolytics.domain.model.ApiKey;
import java.util.Optional;

public interface ApiKeyRepository {
    boolean existsByApiKey(String apiKey);
    void deleteByApiKey(String apiKey);
    Optional<ApiKey> findByApiKey(String apiKey);
    void save(ApiKey apiKey);
}
