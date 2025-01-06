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
        PRICE("Price"),
        VOLUME("Volume"),
        MARKET_CAP("Market_Cap"),
        MARKET_CAP_RANK("Market_Cap_Rank"),
        HIGH_24H("High_24h");

        private final String name;

        IndicatorType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static IndicatorType fromString(String name) {
            for (IndicatorType type : IndicatorType.values()) {
                if (type.name.equalsIgnoreCase(name)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid indicator: " + name);
        }
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

    public BigDecimal getIndicatorValue(String currencySymbol, String indicatorName) {
        // Get currency entity by symbol
        Currency currency = currencyRepository.findBySymbol(currencySymbol)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found: " + currencySymbol));

        // Get Indicator
        Indicator indicator = indicatorRepository.findByName(indicatorName)
                .orElseThrow(() -> new EntityNotFoundException("Indicator not found: " + indicatorName));

        // Find the CurrencyIndicator
        CurrencyIndicator currencyIndicator = currencyIndicatorRepository
                .findByCurrencyAndIndicator(currency, indicator)
                .orElseThrow(() -> new EntityNotFoundException("Currency-Indicator pair not found"));

        // Get the most recent value for this CurrencyIndicator
        CurrencyIndicatorValue value = currencyIndicatorValueRepository
                .findTopByCurrencyIndicatorOrderByTimestampDesc(currencyIndicator)
                .orElseThrow(() -> new EntityNotFoundException("Indicator data not found"));

        return value.getValue();
    }

    public Object getIndicatorForCurrency(String currencySymbol, IndicatorType indicatorType) {
       return getIndicatorValue(currencySymbol, indicatorType.getName());
    }

    public List<Currency> getAllCurrencies(){
        return currencyRepository.findAll();
    }

    public List<Indicator> getAllIndicators(){
        return indicatorRepository.findAll();
    }

    public String getFormattedOldestUpdateTimestamp(){
        LocalDateTime oldestTimestamp = currencyIndicatorValueRepository.findFirstByOrderByTimestampAsc().getTimestamp();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM");
        return oldestTimestamp.format(formatter);
    }
}
