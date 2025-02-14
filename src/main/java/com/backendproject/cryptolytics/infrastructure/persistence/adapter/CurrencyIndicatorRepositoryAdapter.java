package com.backendproject.cryptolytics.infrastructure.persistence.adapter;

import com.backendproject.cryptolytics.domain.model.Currency;
import com.backendproject.cryptolytics.domain.model.CurrencyIndicator;
import com.backendproject.cryptolytics.domain.model.Indicator;
import com.backendproject.cryptolytics.domain.port.out.CurrencyIndicatorRepository;
import com.backendproject.cryptolytics.infrastructure.persistence.mapper.CurrencyIndicatorMapper;
import com.backendproject.cryptolytics.infrastructure.persistence.mapper.CurrencyMapper;
import com.backendproject.cryptolytics.infrastructure.persistence.mapper.IndicatorMapper;
import com.backendproject.cryptolytics.infrastructure.persistence.repository.CurrencyIndicatorEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CurrencyIndicatorRepositoryAdapter implements CurrencyIndicatorRepository {

    private final CurrencyIndicatorEntityRepository currencyIndicatorEntityRepository;
    private final CurrencyIndicatorMapper currencyIndicatorMapper;
    private final CurrencyMapper currencyMapper;
    private final IndicatorMapper indicatorMapper;

    @Override
    public Optional<CurrencyIndicator> findByCurrencyAndIndicator(Currency currency, Indicator indicator) {
        return currencyIndicatorEntityRepository.findByCurrencyAndIndicator(
                currencyMapper.toEntity(currency), indicatorMapper.toEntity(indicator))
                .map(currencyIndicatorMapper::toDomain);
    }
}
