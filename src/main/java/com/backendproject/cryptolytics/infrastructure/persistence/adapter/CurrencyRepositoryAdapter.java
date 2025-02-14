package com.backendproject.cryptolytics.infrastructure.persistence.adapter;

import com.backendproject.cryptolytics.domain.model.Currency;
import com.backendproject.cryptolytics.domain.port.out.CurrencyRepository;
import com.backendproject.cryptolytics.infrastructure.persistence.mapper.CurrencyMapper;
import com.backendproject.cryptolytics.infrastructure.persistence.repository.CurrencyEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public List<Currency> findAll() {
        return currencyEntityRepository.findAll()
                .stream().map(currencyMapper::toDomain).toList();
    }
}