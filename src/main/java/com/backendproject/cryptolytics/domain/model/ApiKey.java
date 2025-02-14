package com.backendproject.cryptolytics.domain.model;

import lombok.Getter;

@Getter
public class ApiKey {
    private Long id;
    private String userId;
    private String apiKey;

    public ApiKey(Long id, String userId, String apiKey) {
        this.id = id;
        this.userId = userId;
        this.apiKey = apiKey;
    }

    public ApiKey(String userId, String apiKey) {
        this(null, userId, apiKey);
    }
}
