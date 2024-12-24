package com.backendproject.cryptolytics.domain.model.entities;

import jakarta.persistence.*;

@Entity
public class QueryTrackedCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "query_id", nullable = false)
    private Query query;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private TrackedCurrency trackedCurrency;

    public QueryTrackedCurrency() {}

    public Long getId() {
        return id;
    }

    public Query getQuery() {
        return query;
    }

    public TrackedCurrency getCurrency() {
        return trackedCurrency;
    }

}
