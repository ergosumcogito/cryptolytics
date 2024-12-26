package com.backendproject.cryptolytics.infrastructure.adapter.rest;

import com.backendproject.cryptolytics.domain.model.entities.Indicator;
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

    @GetMapping("/price")
    public ResponseEntity<BigDecimal> getPrice(@RequestParam String currency) {
        BigDecimal price = cryptoQueryService.getPriceForCurrency(currency);
        return ResponseEntity.ok(price);
    }

    @GetMapping("/indicator")
    public ResponseEntity<Object> getIndicatorForCurrency(
            @RequestParam String currency,
            @RequestParam String indicator) {
        try{
            CryptoQueryService.IndicatorType indicatorType = CryptoQueryService.IndicatorType.valueOf(indicator.toUpperCase());
            Object result  = cryptoQueryService.getIndicatorForCurrency(currency, indicatorType);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported indicator: " + indicator);
        }

    }
}

