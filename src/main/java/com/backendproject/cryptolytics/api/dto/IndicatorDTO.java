package com.backendproject.cryptolytics.api.dto;

public class IndicatorDTO {
    private String name;
    private String description;

    public IndicatorDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
