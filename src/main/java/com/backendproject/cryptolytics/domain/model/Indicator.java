package com.backendproject.cryptolytics.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Indicator {
    private Long id;
    private String name;
    private String description;
}