package com.backendproject.cryptolytics;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.backendproject.cryptolytics.application.service.CryptoQueryService;
import com.backendproject.cryptolytics.domain.model.Currency;
import com.backendproject.cryptolytics.domain.model.CurrencyIndicator;
import com.backendproject.cryptolytics.domain.model.CurrencyIndicatorValue;
import com.backendproject.cryptolytics.domain.model.Indicator;
import com.backendproject.cryptolytics.domain.port.out.CurrencyIndicatorRepository;
import com.backendproject.cryptolytics.domain.port.out.CurrencyIndicatorValueRepository;
import com.backendproject.cryptolytics.domain.port.out.CurrencyRepository;
import com.backendproject.cryptolytics.domain.port.out.IndicatorRepository;
import com.backendproject.cryptolytics.infrastructure.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class CryptoQueryServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private IndicatorRepository indicatorRepository;

    @Mock
    private CurrencyIndicatorRepository currencyIndicatorRepository;

    @Mock
    private CurrencyIndicatorValueRepository currencyIndicatorValueRepository;

    @InjectMocks
    private CryptoQueryService cryptoQueryService;

    @BeforeEach
    public void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetIndicatorValue_success() {
        // Arrange
        String currencySymbol = "BTC";
        String indicatorName = "Price"; // Must match the enum value

        // Create objects using constructors with id as the first parameter
        Currency currency = new Currency(1L, currencySymbol, "Bitcoin");
        Indicator indicator = new Indicator(1L, indicatorName, "Current price");
        CurrencyIndicator currencyIndicator = new CurrencyIndicator(1L, currency, indicator);

        // Fixed timestamp for test stability
        LocalDateTime timestamp = LocalDateTime.of(2025, 2, 15, 12, 0, 0);
        BigDecimal expectedValue = new BigDecimal("50000");
        CurrencyIndicatorValue indicatorValue = new CurrencyIndicatorValue(1L, currencyIndicator, expectedValue, timestamp);

        // Set up mock behavior
        when(currencyRepository.findBySymbol(currencySymbol))
                .thenReturn(Optional.of(currency));
        when(indicatorRepository.findByName(indicatorName))
                .thenReturn(Optional.of(indicator));
        when(currencyIndicatorRepository.findByCurrencyAndIndicator(currency, indicator))
                .thenReturn(Optional.of(currencyIndicator));
        when(currencyIndicatorValueRepository.findTopByCurrencyIndicatorOrderByTimestampDesc(currencyIndicator))
                .thenReturn(Optional.of(indicatorValue));

        // Act
        BigDecimal result = cryptoQueryService.getIndicatorValue(currencySymbol, indicatorName);

        // Assert
        assertEquals(expectedValue, result);
    }

    @Test
    public void testGetIndicatorValue_currencyNotFound() {
        // Arrange
        String currencySymbol = "NON_EXISTENT";
        String indicatorName = "Price";
        when(currencyRepository.findBySymbol(currencySymbol))
                .thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NotFoundException.class, () -> {
            cryptoQueryService.getIndicatorValue(currencySymbol, indicatorName);
        });
        assertTrue(exception.getMessage().contains("Currency not found"));
    }

    @Test
    public void testGetFormattedOldestUpdateTimestamp() {
        // Arrange
        LocalDateTime oldestTimestamp = LocalDateTime.of(2025, 2, 15, 10, 30, 45);
        Currency currency = new Currency(2L, "ETH", "Ethereum");
        Indicator indicator = new Indicator(2L, "Volume", "Trading volume");
        CurrencyIndicator currencyIndicator = new CurrencyIndicator(2L, currency, indicator);
        CurrencyIndicatorValue indicatorValue = new CurrencyIndicatorValue(2L, currencyIndicator, new BigDecimal("12345"), oldestTimestamp);

        when(currencyIndicatorValueRepository.findFirstByOrderByTimestampAsc())
                .thenReturn(indicatorValue);

        // Act
        String formattedTimestamp = cryptoQueryService.getFormattedOldestUpdateTimestamp();

        // Assert: verify the format "HH:mm:ss dd.MM"
        String expected = oldestTimestamp.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss dd.MM"));
        assertEquals(expected, formattedTimestamp);
    }
}
