package com.backendproject.cryptolytics.infrastructure.persistence.mapper;

import com.backendproject.cryptolytics.domain.model.Indicator;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.IndicatorEntity;
import org.springframework.stereotype.Component;

@Component
public class IndicatorMapper {

    public Indicator toDomain(IndicatorEntity entity){
        return new Indicator(entity.getId(), entity.getName(), entity.getDescription());
    }

    public IndicatorEntity toEntity(Indicator domain){
        return new IndicatorEntity(domain.getId(), domain.getName(), domain.getDescription());
    }
}
