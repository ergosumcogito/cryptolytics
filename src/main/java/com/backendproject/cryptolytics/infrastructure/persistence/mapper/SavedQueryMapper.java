package com.backendproject.cryptolytics.infrastructure.persistence.mapper;

import com.backendproject.cryptolytics.domain.model.SavedQuery;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.SavedQueryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SavedQueryMapper {

    private final CurrencyMapper currencyMapper;
    private final IndicatorMapper indicatorMapper;
    private final ApiKeyMapper apiKeyMapper;

    public SavedQuery toDomain(SavedQueryEntity entity){
        return new SavedQuery(
                entity.getId(),
                apiKeyMapper.toDomain(entity.getApiKey()),
                entity.getQueryName(),
                currencyMapper.toDomain(entity.getCurrency()),
                indicatorMapper.toDomain(entity.getIndicator())
        );
    }

    public SavedQueryEntity toEntity(SavedQuery domain){
        return new SavedQueryEntity(
                domain.getId(),
                apiKeyMapper.toEntity(domain.getApiKey()),
                domain.getQueryName(),
                currencyMapper.toEntity(domain.getCurrency()),
                indicatorMapper.toEntity(domain.getIndicator())
        );
    }
}
