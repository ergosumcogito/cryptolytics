package com.backendproject.cryptolytics.domain.port.out;

import com.backendproject.cryptolytics.domain.model.Currency;
import com.backendproject.cryptolytics.domain.model.CurrencyIndicator;
import com.backendproject.cryptolytics.domain.model.Indicator;

import java.util.Optional;

public interface CurrencyIndicatorRepository {
    Optional<CurrencyIndicator> findByCurrencyAndIndicator(Currency currency, Indicator indicator);

}
