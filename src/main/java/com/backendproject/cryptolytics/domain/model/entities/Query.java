package com.backendproject.cryptolytics.domain.model.entities;

import jakarta.persistence.*;

import javax.sound.midi.Track;
import java.util.List;

@Entity
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userInputId;

    @ManyToOne
    @JoinColumn(name = "query_currencies_id")
    private TrackedCurrency currency;

    @ManyToOne
    @JoinColumn(name = "indicator_id", nullable = false)
    private Indicator indicator;

    public Query() {}

    public Long getId() {
        return id;
    }

    public Long getUserInputId() {
        return userInputId;
    }

    public TrackedCurrency getCurrency() {
        return currency;
    }

    public Indicator getIndicator() {
        return indicator;
    }
}
