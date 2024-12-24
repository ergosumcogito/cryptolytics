package com.backendproject.cryptolytics.domain.model.entities;

import jakarta.persistence.*;

@Entity
public class IndicatorTrackedCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "indicator_id", nullable = false)
    private Indicator indicator;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private TrackedCurrency trackedCurrency;

    public Indicator getIndicator() {
        return indicator;
    }

    public TrackedCurrency getTrackedCurrency() {
        return trackedCurrency;
    }
}
