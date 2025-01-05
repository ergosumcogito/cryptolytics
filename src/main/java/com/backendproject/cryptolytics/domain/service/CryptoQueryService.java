package com.backendproject.cryptolytics.domain.service;

import com.backendproject.cryptolytics.persistence.entity.Currency;
import com.backendproject.cryptolytics.persistence.entity.CurrencyIndicator;
import com.backendproject.cryptolytics.persistence.entity.CurrencyIndicatorValue;
import com.backendproject.cryptolytics.persistence.entity.Indicator;
import com.backendproject.cryptolytics.persistence.repository.CurrencyIndicatorRepository;
import com.backendproject.cryptolytics.persistence.repository.CurrencyIndicatorValueRepository;
import com.backendproject.cryptolytics.persistence.repository.CurrencyRepository;
import com.backendproject.cryptolytics.persistence.repository.IndicatorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        return switch (indicatorType) {
            case PRICE -> getPriceForCurrency(currency);
            case VOLUME -> getVolumeForCurrency(currency);
            default -> throw new IllegalArgumentException("Invalid indicator: " + indicatorType);
        };
    }

    public BigDecimal getVolumeForCurrency(String currencySymbol) {
        Currency currency = currencyRepository.findBySymbol(currencySymbol)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found: " + currencySymbol));

        Indicator volumeIndicator = indicatorRepository.findByName("Volume")
                .orElseThrow(() -> new EntityNotFoundException("Indicator 'Volume' not found"));

        CurrencyIndicator currencyIndicator = currencyIndicatorRepository
                .findByCurrencyAndIndicator(currency, volumeIndicator)
                .orElseThrow(() -> new EntityNotFoundException("Currency-Indicator pair not found"));

        CurrencyIndicatorValue value = currencyIndicatorValueRepository
                .findTopByCurrencyIndicatorOrderByTimestampDesc(currencyIndicator)
                .orElseThrow(() -> new EntityNotFoundException("Volume data not found"));

        return value.getValue();
    }

    public List<Currency> getAllCurrencies(){
        return currencyRepository.findAll();
    }

    public String getFormattedOldestUpdateTimestamp(){
        LocalDateTime oldestTimestamp = currencyIndicatorValueRepository.findFirstByOrderByTimestampAsc().getTimestamp();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM");
        return oldestTimestamp.format(formatter);
    }
}
