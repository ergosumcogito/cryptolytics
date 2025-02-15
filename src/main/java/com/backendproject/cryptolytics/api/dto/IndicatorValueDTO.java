package com.backendproject.cryptolytics.api.dto;

public class IndicatorValueDTO {
    private String currency;
    private String indicator;
    private Object value;

    public IndicatorValueDTO(String currency, String indicator, Object value) {
        this.currency = currency;
        this.indicator = indicator;
        this.value = value;
    }

    public String getCurrency() { return currency; }
    public String getIndicator() { return indicator; }
    public Object getValue() { return value; }
}
