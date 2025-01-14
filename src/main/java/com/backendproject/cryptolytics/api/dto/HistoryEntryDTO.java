package com.backendproject.cryptolytics.api.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class HistoryEntryDTO {
    private String date;
    private Map<String, Object> indicators = new HashMap<>();

    public HistoryEntryDTO(LocalDateTime timestamp, String indicatorName, BigDecimal indicatorValue) {
        this.date = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.indicators.put(indicatorName, indicatorValue);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // This method dynamically adds properties to the serialized JSON
    @JsonAnyGetter
    public Map<String, Object> getIndicators() {
        return indicators;
    }

    public void addIndicator(String key, Object value) {
        this.indicators.put(key, value);
    }
}