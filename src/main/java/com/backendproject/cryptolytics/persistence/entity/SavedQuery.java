package com.backendproject.cryptolytics.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SavedQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "api_key_id", nullable = false)
    private ApiKey apiKey;

    private String queryName;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "indicator_id", nullable = false)
    private Indicator indicator;
}
