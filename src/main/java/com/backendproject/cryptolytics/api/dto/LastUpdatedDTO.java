package com.backendproject.cryptolytics.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LastUpdatedDTO {
    private String timestamp;

    public LastUpdatedDTO(String timestamp) {
        this.timestamp = timestamp;
    }
}
