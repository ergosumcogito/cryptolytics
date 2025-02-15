package com.backendproject.cryptolytics.infrastructure.persistence.adapter;

import com.backendproject.cryptolytics.domain.model.Currency;
import com.backendproject.cryptolytics.domain.port.out.CurrencyRepository;
import com.backendproject.cryptolytics.infrastructure.persistence.mapper.CurrencyMapper;
import com.backendproject.cryptolytics.infrastructure.persistence.repository.CurrencyEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CurrencyRepositoryAdapter implements CurrencyRepository {

    private final CurrencyEntityRepository currencyEntityRepository;
    private final CurrencyMapper currencyMapper;

    @Override
    public Optional<Currency> findBySymbol(String symbol) {
        return currencyEntityRepository.findBySymbol(symbol)
                .map(currencyMapper::toDomain);
    }

    @Override
    public Page<Currency> findAll(Pageable pageable) {
        return currencyEntityRepository.findAll(pageable)
                .map(currencyMapper::toDomain);
    }
}