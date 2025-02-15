package com.backendproject.cryptolytics.domain.port.out;

import com.backendproject.cryptolytics.domain.model.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CurrencyRepository {
    Optional<Currency> findBySymbol(String symbol);
    Page<Currency> findAll(Pageable pageable);
}

