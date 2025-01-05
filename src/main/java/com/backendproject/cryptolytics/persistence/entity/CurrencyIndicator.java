package com.backendproject.cryptolytics.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CurrencyIndicator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Indicator indicator;

    // Getters and setters
}