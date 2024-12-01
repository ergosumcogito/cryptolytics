package com.backendproject.cryptolytics.domain.model.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userInput_id", nullable = false)
    private UserInput userInput;

    @ElementCollection
    private List<String> queryCurrencies;

    @OneToMany(mappedBy = "query")
    private List<QueryTrackedCurrencies> queryTrackedCurrencies;

    @ManyToOne
    @JoinColumn(name = "indicator_id", nullable = false)
    private Indicator indicator;

    public Query() {}

    public Long getId() {
        return id;
    }

    public UserInput getUserInput() {
        return userInput;
    }

    public List<String> getQueryCurrencies() {
        return queryCurrencies;
    }

    public List<QueryTrackedCurrencies> getTrackedCurrencies() {
        return queryTrackedCurrencies;
    }

    public Indicator getIndicator() {
        return indicator;
    }
}
