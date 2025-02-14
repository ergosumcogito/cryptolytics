package com.backendproject.cryptolytics.infrastructure.persistence.mapper;

import com.backendproject.cryptolytics.domain.model.Currency;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.CurrencyEntity;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {

    public Currency toDomain(CurrencyEntity entity) {
        return new Currency(entity.getId(), entity.getSymbol(), entity.getName());
    }

    public CurrencyEntity toEntity(Currency model) {
        return new CurrencyEntity(model.getId(), model.getSymbol(), model.getName());
    }
}
