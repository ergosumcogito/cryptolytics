package com.backendproject.cryptolytics.infrastructure.adapter.rest;


import com.backendproject.cryptolytics.domain.service.CryptoQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cryptolytics")
public class CryptolyticsController {

    private final CryptoQueryService cryptoQueryService;

    @Autowired
    public CryptolyticsController(CryptoQueryService cryptoQueryService) {
        this.cryptoQueryService = cryptoQueryService;
    }

    @GetMapping("/indicator")
    public ResponseEntity<BigDecimal> getIndicatorValue(@RequestParam Long queryId) {
        BigDecimal value = cryptoQueryService.getIndicatorValue(queryId);
        return ResponseEntity.ok(value);
    }
}