package com.backendproject.cryptolytics.application.service;

import com.backendproject.cryptolytics.api.dto.SavedQueryDTO;
import com.backendproject.cryptolytics.domain.model.ApiKey;
import com.backendproject.cryptolytics.domain.model.Currency;
import com.backendproject.cryptolytics.domain.model.Indicator;
import com.backendproject.cryptolytics.domain.model.SavedQuery;
import com.backendproject.cryptolytics.domain.port.out.CurrencyRepository;
import com.backendproject.cryptolytics.domain.port.out.IndicatorRepository;
import com.backendproject.cryptolytics.domain.port.out.SavedQueryRepository;
import com.backendproject.cryptolytics.infrastructure.exceptions.NotFoundException;
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
    private final IndicatorRepository indicatorRepository;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public SavedQueryService(SavedQueryRepository savedQueryRepository,
                             CryptoQueryService cryptoQueryService, IndicatorRepository indicatorRepository,
                             CurrencyRepository currencyRepository) {
        this.savedQueryRepository = savedQueryRepository;
        this.cryptoQueryService = cryptoQueryService;
        this.indicatorRepository = indicatorRepository;
        this.currencyRepository = currencyRepository;
    }

    public Optional<SavedQuery> findByQueryNameAndApiKey(String queryName, ApiKey apiKey) {
        return savedQueryRepository.findByQueryNameAndApiKey(queryName, apiKey);
    }


    public String getSavedQueryIndicatorValue(String queryName, ApiKey apiKey) {
        SavedQuery savedQuery = findByQueryNameAndApiKey(queryName, apiKey)
                .orElseThrow(() -> new NotFoundException("Saved query not found: " + queryName));

        return cryptoQueryService.getIndicatorValue(
                savedQuery.getCurrency().getSymbol(),
                savedQuery.getIndicator().getName()
        ).toString();
    }

    public List<SavedQueryDTO> getAllSavedQueriesByApiKey(ApiKey apiKey) {
        List<SavedQuery> savedQueries = savedQueryRepository.findAllByApiKey(apiKey);

        return savedQueries.stream()
                .map(savedQuery -> new SavedQueryDTO(
                        savedQuery.getQueryName(),
                        savedQuery.getCurrency().getSymbol(),
                        savedQuery.getIndicator().getName()
                ))
                .collect(Collectors.toList());
    }

    public void createSavedQuery(ApiKey apiKey, String queryName, String currencySymbol, String indicatorName) {
        CryptoQueryService.IndicatorType indicatorType = CryptoQueryService.IndicatorType.fromString(indicatorName);

        // Find the currency by its symbol
        Currency currency = currencyRepository.findBySymbol(currencySymbol)
                .orElseThrow(() -> new NotFoundException("Currency not found: " + currencySymbol));

        // Find the indicator by its name
        Indicator indicator = indicatorRepository.findByName(indicatorType.getName())
                .orElseThrow(() -> new NotFoundException("Indicator not found: " + indicatorType.getName()));

        // Check if a saved query with the same name already exists for the API key
        if (savedQueryRepository.existsByApiKeyAndQueryName(apiKey, queryName)) {
            throw new IllegalArgumentException("A saved query with this name already exists");
        }

        // Create and save the new Saved Query
        SavedQuery savedQuery = new SavedQuery(apiKey, queryName, currency, indicator);
        savedQueryRepository.save(savedQuery);
    }

    public void deleteSavedQuery(ApiKey apiKey, String queryName) {
        SavedQuery savedQuery = savedQueryRepository.findByQueryNameAndApiKey(queryName, apiKey)
                .orElseThrow(() -> new NotFoundException("Saved query not found"));
        savedQueryRepository.delete(savedQuery);
    }

    public void updateSavedQuery(ApiKey apiKey, String existingQueryName, String newQueryName, String newCurrency, String newIndicator) {
        SavedQuery savedQuery = savedQueryRepository.findByQueryNameAndApiKey(existingQueryName, apiKey)
                .orElseThrow(() -> new NotFoundException("Saved query not found"));

        Currency currency = currencyRepository.findBySymbol(newCurrency)
                .orElseThrow(() -> new NotFoundException("Currency not found"));

        CryptoQueryService.IndicatorType indicatorType = CryptoQueryService.IndicatorType.fromString(newIndicator);

        Indicator indicator = indicatorRepository.findByName(indicatorType.getName())
                .orElseThrow(() -> new NotFoundException("Indicator not found"));

        savedQuery.setQueryName(newQueryName);
        savedQuery.setCurrency(currency);
        savedQuery.setIndicator(indicator);

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