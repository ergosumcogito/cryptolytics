package com.backendproject.cryptolytics.infrastructure.persistence.mapper;

import com.backendproject.cryptolytics.domain.model.ApiKey;
import com.backendproject.cryptolytics.infrastructure.persistence.entity.ApiKeyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiKeyMapper {

    public ApiKey toDomain(ApiKeyEntity entity){
        return new ApiKey(entity.getId(), entity.getUserId(), entity.getApiKey());
    }

    public ApiKeyEntity toEntity(ApiKey domain){
        return new ApiKeyEntity(domain.getId(), domain.getUserId(), domain.getApiKey());
    }
}
