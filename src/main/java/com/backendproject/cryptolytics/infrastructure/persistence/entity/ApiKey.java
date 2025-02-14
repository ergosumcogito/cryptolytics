package com.backendproject.cryptolytics.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "Api_Key")
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String apiKey;

    public ApiKey() {}

    public ApiKey(String userId, String apiKey) {
        this.userId = userId;
        this.apiKey = apiKey;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}

