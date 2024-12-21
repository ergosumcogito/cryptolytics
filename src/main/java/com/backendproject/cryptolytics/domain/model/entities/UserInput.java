package com.backendproject.cryptolytics.domain.model.entities;

import jakarta.persistence.*;

@Entity
public class UserInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String indicatorName;
    private String currenciesNames;

    @OneToOne(mappedBy = "userInput")
    private Query query;

    public UserInput() {}

    public UserInput(String indicatorName, String currenciesNames) {
        this.indicatorName = indicatorName;
        this.currenciesNames = currenciesNames;
    }

    public Long getId() {
        return id;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public String getCurrenciesNames() {
        return currenciesNames;
    }

    public Query getQuery() {
        return query;
    }
}
