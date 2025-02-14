package com.backendproject.cryptolytics.domain.port.out;

import com.backendproject.cryptolytics.domain.model.Currency;
import java.util.List;
import java.util.Optional;

public interface CurrencyRepository {
    Optional<Currency> findBySymbol(String symbol);
    List<Currency> findAll();
}

