package com.backendproject.cryptolytics.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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
}

