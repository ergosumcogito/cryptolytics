package com.backendproject.cryptolytics.application.service;

import com.backendproject.cryptolytics.api.dto.CurrencyDTO;
import com.backendproject.cryptolytics.domain.model.Currency;
import com.backendproject.cryptolytics.domain.model.CurrencyIndicator;
import com.backendproject.cryptolytics.domain.model.CurrencyIndicatorValue;
import com.backendproject.cryptolytics.domain.model.Indicator;
import com.backendproject.cryptolytics.domain.port.out.CurrencyIndicatorRepository;
import com.backendproject.cryptolytics.domain.port.out.CurrencyIndicatorValueRepository;
import com.backendproject.cryptolytics.domain.port.out.CurrencyRepository;
import com.backendproject.cryptolytics.domain.port.out.IndicatorRepository;
import com.backendproject.cryptolytics.infrastructure.exceptions.NotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Getter
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
        IndicatorType indicatorType = IndicatorType.fromString(indicatorName);

        // Get currency by symbol
        Currency currency = currencyRepository.findBySymbol(currencySymbol)
                .orElseThrow(() -> new NotFoundException("Currency not found: " + currencySymbol));

        // Get Indicator
        Indicator indicator = indicatorRepository.findByName(indicatorType.getName())
                .orElseThrow(() -> new NotFoundException("Indicator not found: " + indicatorType.getName()));

        // Find the CurrencyIndicator
        CurrencyIndicator currencyIndicator = currencyIndicatorRepository
                .findByCurrencyAndIndicator(currency, indicator)
                .orElseThrow(() -> new NotFoundException("Currency-Indicator pair not found"));

        // Get the most recent value for this CurrencyIndicator
        CurrencyIndicatorValue value = currencyIndicatorValueRepository
                .findTopByCurrencyIndicatorOrderByTimestampDesc(currencyIndicator)
                .orElseThrow(() -> new NotFoundException("Indicator data not found"));

        return value.getValue();
    }

    public Page<CurrencyDTO> getAllCurrencies(Pageable pageable){
        return currencyRepository.findAll(pageable)
                .map(currency -> new CurrencyDTO(currency.getSymbol(), currency.getName()));
    }

    public List<Indicator> getAllIndicators(){
        return indicatorRepository.findAll();
    }

    public String getFormattedOldestUpdateTimestamp(){
        LocalDateTime oldestTimestamp = currencyIndicatorValueRepository.findFirstByOrderByTimestampAsc().getTimestamp();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM");
        return oldestTimestamp.format(formatter);
    }

    public Page<CurrencyIndicatorValue> getIndicatorHistory(String currencySymbol, String indicatorName,
                                                            String startDate, String endDate, Pageable pageable){
        IndicatorType indicatorType = IndicatorType.fromString(indicatorName);

        // Get currency by symbol
        Currency currency = currencyRepository.findBySymbol(currencySymbol)
                .orElseThrow(() -> new NotFoundException("Currency not found: " + currencySymbol));

        // Get Indicator
        Indicator indicator = indicatorRepository.findByName(indicatorType.getName())
                .orElseThrow(() -> new NotFoundException("Indicator not found: " + indicatorName));

        // Find the CurrencyIndicator
        CurrencyIndicator currencyIndicator = currencyIndicatorRepository
                .findByCurrencyAndIndicator(currency, indicator)
                .orElseThrow(() -> new NotFoundException("Currency-Indicator pair not found"));

        // Query the database with startDate and endDate
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : null;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : null;

        if (start != null && end != null) {
            return currencyIndicatorValueRepository
                    .findByCurrencyIndicatorAndTimestampBetween(currencyIndicator, start, end, pageable);
        } else {
            return currencyIndicatorValueRepository
                    .findByCurrencyIndicatorOrderByTimestampDesc(currencyIndicator, pageable);
        }
    }
}
