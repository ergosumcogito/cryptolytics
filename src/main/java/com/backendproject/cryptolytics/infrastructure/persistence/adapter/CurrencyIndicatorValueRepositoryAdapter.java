package com.backendproject.cryptolytics.infrastructure.persistence.adapter;

import com.backendproject.cryptolytics.domain.model.CurrencyIndicator;
import com.backendproject.cryptolytics.domain.model.CurrencyIndicatorValue;
import com.backendproject.cryptolytics.domain.port.out.CurrencyIndicatorValueRepository;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.CurrencyIndicatorValueEntity;
import com.backendproject.cryptolytics.infrastructure.persistence.mapper.CurrencyIndicatorMapper;
import com.backendproject.cryptolytics.infrastructure.persistence.mapper.CurrencyIndicatorValueMapper;
import com.backendproject.cryptolytics.infrastructure.persistence.repository.CurrencyIndicatorValueEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        CurrencyIndicatorValueEntity entity = currencyIndicatorValueEntityRepository.findFirstByOrderByTimestampAsc();
        return entity != null ? currencyIndicatorValueMapper.toDomain(entity) : null;
    }

    @Override
    public Page<CurrencyIndicatorValue> findByCurrencyIndicatorOrderByTimestampDesc(CurrencyIndicator currencyIndicator, Pageable pageable) {
        return currencyIndicatorValueEntityRepository.findByCurrencyIndicatorOrderByTimestampDesc(
                currencyIndicatorMapper.toEntity(currencyIndicator), pageable
        ).map(currencyIndicatorValueMapper::toDomain);
    }

    @Override
    public Page<CurrencyIndicatorValue> findByCurrencyIndicatorAndTimestampBetween(CurrencyIndicator currencyIndicator, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return currencyIndicatorValueEntityRepository.findByCurrencyIndicatorAndTimestampBetween(
                currencyIndicatorMapper.toEntity(currencyIndicator), start, end, pageable
        ).map(currencyIndicatorValueMapper::toDomain);
    }
}
