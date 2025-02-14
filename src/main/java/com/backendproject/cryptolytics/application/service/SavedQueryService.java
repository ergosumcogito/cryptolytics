package com.backendproject.cryptolytics.application.service;

import com.backendproject.cryptolytics.api.dto.SavedQueryDTO;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.ApiKey;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.CurrencyEntity;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.IndicatorEntity;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.SavedQuery;
import com.backendproject.cryptolytics.infrastructure.persistence.repository.CurrencyEntityRepository;
import com.backendproject.cryptolytics.infrastructure.persistence.repository.IndicatorEntityRepository;
import com.backendproject.cryptolytics.infrastructure.persistence.repository.SavedQueryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SavedQueryService {

    private final SavedQueryRepository savedQueryRepository;
    private final CryptoQueryService cryptoQueryService;
    private final IndicatorEntityRepository indicatorEntityRepository;
    private final CurrencyEntityRepository currencyEntityRepository;

    @Autowired
    public SavedQueryService(SavedQueryRepository savedQueryRepository,
                             CryptoQueryService cryptoQueryService, IndicatorEntityRepository indicatorEntityRepository,
                             CurrencyEntityRepository currencyEntityRepository) {
        this.savedQueryRepository = savedQueryRepository;
        this.cryptoQueryService = cryptoQueryService;
        this.indicatorEntityRepository = indicatorEntityRepository;
        this.currencyEntityRepository = currencyEntityRepository;
    }

    public Optional<SavedQuery> findByQueryNameAndApiKey(String queryName, ApiKey apiKey) {
        return savedQueryRepository.findByQueryNameAndApiKey(queryName, apiKey);
    }


    public String getSavedQueryIndicatorValue(String queryName, ApiKey apiKey) {
        SavedQuery savedQuery = findByQueryNameAndApiKey(queryName, apiKey)
                .orElseThrow(() -> new EntityNotFoundException("Saved query not found: " + queryName));

        return cryptoQueryService.getIndicatorValue(
                savedQuery.getCurrencyEntity().getSymbol(),
                savedQuery.getIndicatorEntity().getName()
        ).toString();
    }

    public List<SavedQueryDTO> getAllSavedQueriesByApiKey(ApiKey apiKey) {
        List<SavedQuery> savedQueries = savedQueryRepository.findAllByApiKey(apiKey);

        return savedQueries.stream()
                .map(savedQuery -> new SavedQueryDTO(
                        savedQuery.getQueryName(),
                        savedQuery.getCurrencyEntity().getSymbol(),
                        savedQuery.getIndicatorEntity().getName()
                ))
                .collect(Collectors.toList());
    }

    public void createSavedQuery(ApiKey apiKey, String queryName, String currencySymbol, String indicatorName) {
        CryptoQueryService.IndicatorType indicatorType = CryptoQueryService.IndicatorType.fromString(indicatorName);

        // Find the currency by its symbol
        CurrencyEntity currencyEntity = currencyEntityRepository.findBySymbol(currencySymbol)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found: " + currencySymbol));

        // Find the indicator by its name
        IndicatorEntity indicatorEntity = indicatorEntityRepository.findByName(indicatorType.getName())
                .orElseThrow(() -> new EntityNotFoundException("Indicator not found: " + indicatorType.getName())); // TODO: probably irrelevant, exception already handled

        // Check if a saved query with the same name already exists for the API key
        if (savedQueryRepository.existsByApiKeyAndQueryName(apiKey, queryName)) {
            throw new IllegalArgumentException("A saved query with this name already exists");
        }

        // Create and save the new Saved Query
        SavedQuery savedQuery = new SavedQuery();
        savedQuery.setApiKey(apiKey);
        savedQuery.setQueryName(queryName);
        savedQuery.setCurrencyEntity(currencyEntity);
        savedQuery.setIndicatorEntity(indicatorEntity);
        savedQueryRepository.save(savedQuery);
    }

    public void deleteSavedQuery(ApiKey apiKey, String queryName) {
        SavedQuery savedQuery = savedQueryRepository.findByQueryNameAndApiKey(queryName, apiKey)
                .orElseThrow(() -> new EntityNotFoundException("Saved query not found"));
        savedQueryRepository.delete(savedQuery);
    }

    public void updateSavedQuery(ApiKey apiKey, String existingQueryName, String newQueryName, String newCurrency, String newIndicator) {
        SavedQuery savedQuery = savedQueryRepository.findByQueryNameAndApiKey(existingQueryName, apiKey)
                .orElseThrow(() -> new EntityNotFoundException("Saved query not found"));

        CurrencyEntity currencyEntity = currencyEntityRepository.findBySymbol(newCurrency)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found"));

        CryptoQueryService.IndicatorType indicatorType = CryptoQueryService.IndicatorType.fromString(newIndicator);

        IndicatorEntity indicatorEntity = indicatorEntityRepository.findByName(indicatorType.getName())
                .orElseThrow(() -> new EntityNotFoundException("Indicator not found")); // TODO: probably irrelevant, exception is already handled

        savedQuery.setQueryName(newQueryName);
        savedQuery.setCurrencyEntity(currencyEntity);
        savedQuery.setIndicatorEntity(indicatorEntity);

        savedQueryRepository.save(savedQuery);
    }

    @Transactional
    public void deleteAllSavedQueriesByApiKey(ApiKey apiKey) {
        List<SavedQuery> savedQueries = savedQueryRepository.findAllByApiKey(apiKey);

        if (!savedQueries.isEmpty()) {
           savedQueryRepository.deleteAll(savedQueries);
        }
    }

}