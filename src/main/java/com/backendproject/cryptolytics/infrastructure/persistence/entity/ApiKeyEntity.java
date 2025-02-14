package com.backendproject.cryptolytics.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "Api_Key")
public class ApiKeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String apiKey;

    public ApiKeyEntity(String userId, String apiKey) {
        this.userId = userId;
        this.apiKey = apiKey;
    }

    public ApiKeyEntity(Long id, String userId, String apiKey) {
        this.id = id;
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

