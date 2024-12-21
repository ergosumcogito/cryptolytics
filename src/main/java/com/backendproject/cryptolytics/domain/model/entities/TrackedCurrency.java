package com.backendproject.cryptolytics.domain.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "trackedcurrency")
public class TrackedCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private String name;

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setName(String name) {
        this.name = name;
    }
}
