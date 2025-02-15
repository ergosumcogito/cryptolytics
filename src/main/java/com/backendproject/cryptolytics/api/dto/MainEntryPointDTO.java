package com.backendproject.cryptolytics.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainEntryPointDTO {
    private String message;
    private String selfLink;
    private String currenciesLink;
    private String indicatorsLink;
    private String lastUpdatedLink;
}