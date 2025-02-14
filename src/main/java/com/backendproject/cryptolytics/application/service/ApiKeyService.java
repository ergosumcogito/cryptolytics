package com.backendproject.cryptolytics.application.service;

import com.backendproject.cryptolytics.infrastructure.persistence.entity.ApiKey;
import com.backendproject.cryptolytics.infrastructure.persistence.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    @Autowired
    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public String generateApiKeyForUser(String userId) {
        String apiKey = UUID.randomUUID().toString();
        apiKeyRepository.save(new ApiKey(userId, apiKey));
        return apiKey;
    }

    @Transactional
    public boolean deleteApiKey(String apiKey) {
        if (apiKeyRepository.existsByApiKey(apiKey)) {
            apiKeyRepository.deleteByApiKey(apiKey);
            return true;
        }
        return false;
    }

    public ApiKey findByApiKey(String apiKey) {
        return apiKeyRepository.findByApiKey(apiKey)
                .orElse(null);
    }



}

