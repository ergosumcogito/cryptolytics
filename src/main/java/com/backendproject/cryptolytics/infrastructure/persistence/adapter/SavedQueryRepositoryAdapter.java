package com.backendproject.cryptolytics.infrastructure.persistence.adapter;

import com.backendproject.cryptolytics.domain.model.ApiKey;
import com.backendproject.cryptolytics.domain.model.SavedQuery;
import com.backendproject.cryptolytics.domain.port.out.SavedQueryRepository;
import com.backendproject.cryptolytics.infrastructure.persistence.mapper.ApiKeyMapper;
import com.backendproject.cryptolytics.infrastructure.persistence.mapper.SavedQueryMapper;
import com.backendproject.cryptolytics.infrastructure.persistence.repository.SavedQueryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SavedQueryRepositoryAdapter implements SavedQueryRepository {

    private final SavedQueryEntityRepository savedQueryEntityRepository;
    private final SavedQueryMapper savedQueryMapper;
    private final ApiKeyMapper apiKeyMapper;

    @Override
    public Optional<SavedQuery> findByQueryNameAndApiKey(String queryName, ApiKey apiKey) {
        return savedQueryEntityRepository.findByQueryNameAndApiKey(
                queryName, apiKeyMapper.toEntity(apiKey))
                .map(savedQueryMapper::toDomain);
    }

    @Override
    public List<SavedQuery> findAllByApiKey(ApiKey apiKey) {
        return savedQueryEntityRepository.findAllByApiKey(
                apiKeyMapper.toEntity(apiKey))
                .stream().map(savedQueryMapper::toDomain).toList();
    }

    @Override
    public boolean existsByApiKeyAndQueryName(ApiKey apiKey, String queryName) {
        return savedQueryEntityRepository.existsByApiKeyAndQueryName(
                apiKeyMapper.toEntity(apiKey), queryName);
    }

    @Override
    public void save(SavedQuery savedQuery) {
        savedQueryEntityRepository.save(savedQueryMapper.toEntity(savedQuery));
    }

    @Override
    public void delete(SavedQuery savedQuery) {
        savedQueryEntityRepository.delete(savedQueryMapper.toEntity(savedQuery));
    }

    @Override
    public void deleteAll(List<SavedQuery> savedQueries) {
        savedQueryEntityRepository.deleteAll(savedQueries.stream().map(savedQueryMapper::toEntity).toList());
    }
}
