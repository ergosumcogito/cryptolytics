package com.backendproject.cryptolytics.infrastructure.persistence.adapter;

import com.backendproject.cryptolytics.domain.model.CurrencyIndicator;
import com.backendproject.cryptolytics.domain.model.CurrencyIndicatorValue;
import com.backendproject.cryptolytics.domain.port.out.CurrencyIndicatorValueRepository;
import com.backendproject.cryptolytics.infrastructure.persistence.mapper.CurrencyIndicatorMapper;
import com.backendproject.cryptolytics.infrastructure.persistence.mapper.CurrencyIndicatorValueMapper;
import com.backendproject.cryptolytics.infrastructure.persistence.repository.CurrencyIndicatorValueEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CurrencyIndicatorValueRepositoryAdapter implements CurrencyIndicatorValueRepository {

    private final CurrencyIndicatorValueEntityRepository currencyIndicatorValueEntityRepository;
    private final CurrencyIndicatorValueMapper currencyIndicatorValueMapper;
    private final CurrencyIndicatorMapper currencyIndicatorMapper;

    @Override
    public List<CurrencyIndicatorValue> findByCurrencyIndicator(CurrencyIndicator currencyIndicator) {
        return currencyIndicatorValueEntityRepository.findByCurrencyIndicator(
                currencyIndicatorMapper.toEntity(currencyIndicator)
        ).stream().map(currencyIndicatorValueMapper::toDomain).toList();
    }

    @Override
    public Optional<CurrencyIndicatorValue> findTopByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicator currencyIndicator) {
        return currencyIndicatorValueEntityRepository.findTopByCurrencyIndicatorOrderByTimestampDesc(
                currencyIndicatorMapper.toEntity(currencyIndicator)
        ).map(currencyIndicatorValueMapper::toDomain);
    }

    @Override
    public CurrencyIndicatorValue findFirstByOrderByTimestampAsc() {
        return currencyIndicatorValueEntityRepository.findFirstByOrderByTimestampAsc() != null
                ? currencyIndicatorValueMapper.toDomain(currencyIndicatorValueEntityRepository.findFirstByOrderByTimestampAsc())
                : null;
    }

    @Override
    public List<CurrencyIndicatorValue> findByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicator currencyIndicator) {
        return currencyIndicatorValueEntityRepository.findByCurrencyIndicatorOrderByTimestampDesc(
                currencyIndicatorMapper.toEntity(currencyIndicator)
        ).stream().map(currencyIndicatorValueMapper::toDomain).toList();
    }

    @Override
    public List<CurrencyIndicatorValue> findByCurrencyIndicatorAndTimestampBetween(CurrencyIndicator currencyIndicator, LocalDateTime start, LocalDateTime end) {
        return currencyIndicatorValueEntityRepository.findByCurrencyIndicatorAndTimestampBetween(
                currencyIndicatorMapper.toEntity(currencyIndicator), start, end
        ).stream().map(currencyIndicatorValueMapper::toDomain).toList();
    }
}
