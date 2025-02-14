package com.backendproject.cryptolytics.infrastructure.persistence.adapter;

import com.backendproject.cryptolytics.domain.model.ApiKey;
import com.backendproject.cryptolytics.domain.port.out.ApiKeyRepository;
import com.backendproject.cryptolytics.infrastructure.persistence.mapper.ApiKeyMapper;
import com.backendproject.cryptolytics.infrastructure.persistence.repository.ApiKeyEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ApiKeyRepositoryAdapter implements ApiKeyRepository {

    private final ApiKeyEntityRepository apiKeyEntityRepository;
    private final ApiKeyMapper apiKeyMapper;

    @Override
    public boolean existsByApiKey(String apiKey) {
        return apiKeyEntityRepository.existsByApiKey(apiKey);
    }

    @Override
    public void deleteByApiKey(String apiKey) {
        apiKeyEntityRepository.deleteByApiKey(apiKey);
    }

    @Override
    public Optional<ApiKey> findByApiKey(String apiKey) {
        return apiKeyEntityRepository.findByApiKey(apiKey).map(apiKeyMapper::toDomain);
    }

    @Override
    public void save(ApiKey apiKey) {
                apiKeyEntityRepository.save(apiKeyMapper.toEntity(apiKey));
    }
}
