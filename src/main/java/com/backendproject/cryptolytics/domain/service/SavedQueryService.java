package com.backendproject.cryptolytics.domain.service;

import com.backendproject.cryptolytics.persistence.entity.ApiKey;
import com.backendproject.cryptolytics.persistence.entity.Currency;
import com.backendproject.cryptolytics.persistence.entity.Indicator;
import com.backendproject.cryptolytics.persistence.entity.SavedQuery;
import com.backendproject.cryptolytics.persistence.repository.SavedQueryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SavedQueryService {

    private final SavedQueryRepository savedQueryRepository;
    private final CryptoQueryService cryptoQueryService;

    @Autowired
    public SavedQueryService(SavedQueryRepository savedQueryRepository, CryptoQueryService cryptoQueryService) {
        this.savedQueryRepository = savedQueryRepository;
        this.cryptoQueryService = cryptoQueryService;
    }


    public SavedQuery saveQuery(String queryName, ApiKey apiKey, Currency currency, Indicator indicator) {
        SavedQuery savedQuery = new SavedQuery();
        savedQuery.setQueryName(queryName);
        savedQuery.setApiKey(apiKey);
        savedQuery.setCurrency(currency);
        savedQuery.setIndicator(indicator);

        return savedQueryRepository.save(savedQuery);
    }

    public Optional<SavedQuery> findByQueryNameAndApiKey(String queryName, ApiKey apiKey) {
        return savedQueryRepository.findByQueryNameAndApiKey(queryName, apiKey);
    }


    public String getSavedQueryIndicatorValue(String queryName, ApiKey apiKey) {
        SavedQuery savedQuery = findByQueryNameAndApiKey(queryName, apiKey)
                .orElseThrow(() -> new EntityNotFoundException("Saved query not found: " + queryName));

        return cryptoQueryService.getIndicatorValue(
                savedQuery.getCurrency().getSymbol(),
                savedQuery.getIndicator().getName()
        ).toString();
    }
}