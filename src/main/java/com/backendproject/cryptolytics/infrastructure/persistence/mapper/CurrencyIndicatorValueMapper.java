package com.backendproject.cryptolytics.infrastructure.persistence.mapper;

import com.backendproject.cryptolytics.domain.model.CurrencyIndicatorValue;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.CurrencyIndicatorValueEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyIndicatorValueMapper {

    private final CurrencyIndicatorMapper currencyIndicatorMapper;

    public CurrencyIndicatorValue toDomain(CurrencyIndicatorValueEntity entity){
        return new CurrencyIndicatorValue(
                entity.getId(),
                currencyIndicatorMapper.toDomain(entity.getCurrencyIndicator()),
                entity.getValue(),
                entity.getTimestamp()
        );
    }

    public CurrencyIndicatorValueEntity toEntity(CurrencyIndicatorValue domain){
        return new CurrencyIndicatorValueEntity(
                domain.getId(),
                currencyIndicatorMapper.toEntity(domain.getCurrencyIndicator()),
                domain.getValue(),
                domain.getTimestamp()
        );
    }
}
