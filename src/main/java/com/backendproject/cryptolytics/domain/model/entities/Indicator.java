package com.backendproject.cryptolytics.domain.model.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Indicator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
