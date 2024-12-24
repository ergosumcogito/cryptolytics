package com.backendproject.cryptolytics.domain.service;


import com.backendproject.cryptolytics.domain.model.entities.APIData;
import com.backendproject.cryptolytics.domain.model.entities.IndicatorTrackedCurrency;
import com.backendproject.cryptolytics.domain.model.entities.Query;
import com.backendproject.cryptolytics.infrastructure.adapter.persistence.APIDataRepository;
import com.backendproject.cryptolytics.infrastructure.adapter.persistence.IndicatorTrackedCurrencyRepository;
import com.backendproject.cryptolytics.infrastructure.adapter.persistence.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CryptoQueryService {

    private final QueryRepository queryRepository;
    private final IndicatorTrackedCurrencyRepository indicatorTrackedCurrencyRepository;
    private final APIDataRepository apiDataRepository;

    @Autowired
    public CryptoQueryService(QueryRepository queryRepository,
                              IndicatorTrackedCurrencyRepository indicatorTrackedCurrencyRepository,
                              APIDataRepository apiDataRepository) {
        this.queryRepository = queryRepository;
        this.indicatorTrackedCurrencyRepository = indicatorTrackedCurrencyRepository;
        this.apiDataRepository = apiDataRepository;
    }

    public BigDecimal getIndicatorValue(Long queryId) {
        Query query = queryRepository.findById(queryId)
                .orElseThrow(() -> new RuntimeException("Query not found"));

        // Fetch IndicatorTrackedCurrency (joining indicator and currency)
        IndicatorTrackedCurrency indicatorTrackedCurrency = indicatorTrackedCurrencyRepository
                .findByIndicatorAndTrackedCurrency(query.getIndicator(), query.getCurrency())
                .orElseThrow(() -> new RuntimeException("Indicator-Currency pair not found"));

        // Fetch APIData for the specific indicator and currency
        APIData apiData = apiDataRepository.findByIndicatorTrackedCurrencyAndTimestamp(indicatorTrackedCurrency, LocalDateTime.now())
                .orElseThrow(() -> new RuntimeException("API data not found"));

        return apiData.getValue();
    }
}
