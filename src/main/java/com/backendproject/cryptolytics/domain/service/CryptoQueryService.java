package com.backendproject.cryptolytics.domain.service;

import com.backendproject.cryptolytics.domain.model.entities.Currency;
import com.backendproject.cryptolytics.domain.model.entities.CurrencyIndicator;
import com.backendproject.cryptolytics.domain.model.entities.CurrencyIndicatorValue;
import com.backendproject.cryptolytics.domain.model.entities.Indicator;
import com.backendproject.cryptolytics.infrastructure.adapter.persistence.CurrencyIndicatorRepository;
import com.backendproject.cryptolytics.infrastructure.adapter.persistence.CurrencyIndicatorValueRepository;
import com.backendproject.cryptolytics.infrastructure.adapter.persistence.CurrencyRepository;
import com.backendproject.cryptolytics.infrastructure.adapter.persistence.IndicatorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CryptoQueryService {

    private final CurrencyRepository currencyRepository;
    private final IndicatorRepository indicatorRepository;
    private final CurrencyIndicatorRepository currencyIndicatorRepository;
    private final CurrencyIndicatorValueRepository currencyIndicatorValueRepository;

    public enum IndicatorType {
        PRICE, VOLUME
    }

    @Autowired
    public CryptoQueryService(CurrencyRepository currencyRepository,
                              IndicatorRepository indicatorRepository,
                              CurrencyIndicatorRepository currencyIndicatorRepository,
                              CurrencyIndicatorValueRepository currencyIndicatorValueRepository) {
        this.currencyRepository = currencyRepository;
        this.indicatorRepository = indicatorRepository;
        this.currencyIndicatorRepository = currencyIndicatorRepository;
        this.currencyIndicatorValueRepository = currencyIndicatorValueRepository;
    }

    public BigDecimal getPriceForCurrency(String currencySymbol) {
        // Get currency entity by symbol
        Currency currency = currencyRepository.findBySymbol(currencySymbol)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found: " + currencySymbol));

        // Get "Price" indicator
        Indicator priceIndicator = indicatorRepository.findByName("Price")
                .orElseThrow(() -> new EntityNotFoundException("Indicator 'Price' not found"));

        // Find the CurrencyIndicator (currency + price indicator)
        CurrencyIndicator currencyIndicator = currencyIndicatorRepository
                .findByCurrencyAndIndicator(currency, priceIndicator)
                .orElseThrow(() -> new EntityNotFoundException("Currency-Indicator pair not found"));

        // Get the most recent value for this CurrencyIndicator
        CurrencyIndicatorValue value = currencyIndicatorValueRepository
                .findTopByCurrencyIndicatorOrderByTimestampDesc(currencyIndicator)
                .orElseThrow(() -> new EntityNotFoundException("Price data not found"));

        return value.getValue();
    }

    public Object getIndicatorForCurrency(String currency, IndicatorType indicatorType) {
        switch (indicatorType) {
            case PRICE:
                return getPriceForCurrency(currency);
            //case "volume":
              //  return getVolumeForCurrency(currency);
            default:
                throw new IllegalArgumentException("Invalid indicator: " + indicatorType);
        }
    }
}
