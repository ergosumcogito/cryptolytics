package com.backendproject.cryptolytics.infrastructure.persistence.mapper;

import com.backendproject.cryptolytics.domain.model.CurrencyIndicator;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.CurrencyIndicatorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyIndicatorMapper {

    private final CurrencyMapper currencyMapper;
    private final IndicatorMapper indicatorMapper;

    public CurrencyIndicator toDomain(CurrencyIndicatorEntity entity){
        return new CurrencyIndicator(
                entity.getId(),
                currencyMapper.toDomain(entity.getCurrency()),
                indicatorMapper.toDomain(entity.getIndicator()));
    }

    public CurrencyIndicatorEntity toEntity(CurrencyIndicator domain){
        return new CurrencyIndicatorEntity(
          domain.getId(),
          currencyMapper.toEntity(domain.getCurrency()),
          indicatorMapper.toEntity(domain.getIndicator())
        );
    }
}
