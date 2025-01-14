package com.backendproject.cryptolytics.api.controller;

import com.backendproject.cryptolytics.api.dto.CurrencyDTO;
import com.backendproject.cryptolytics.api.dto.IndicatorDTO;
import com.backendproject.cryptolytics.domain.service.CryptoQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cryptolytics")
public class CryptolyticsController {

    private final CryptoQueryService cryptoQueryService;

    @Autowired
    public CryptolyticsController(CryptoQueryService cryptoQueryService) {
        this.cryptoQueryService = cryptoQueryService;
    }

    @GetMapping("/currencies/{currency}/{indicator}")
    public ResponseEntity<Object> getIndicatorForCurrency(
            @PathVariable String currency,
            @PathVariable String indicator) {
        try{
            CryptoQueryService.IndicatorType indicatorType = CryptoQueryService.IndicatorType.valueOf(indicator.toUpperCase());
            Object result  = cryptoQueryService.getIndicatorForCurrency(currency, indicatorType);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported indicator: " + indicator);
        }

    }

    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyDTO>> getCurrenciesList(){
        List<CurrencyDTO> currencyDTOs  = cryptoQueryService.getAllCurrencies().stream()
                .map(currency -> new CurrencyDTO(currency.getSymbol(), currency.getName()))
                .toList();
        return ResponseEntity.ok(currencyDTOs);
    }

    @GetMapping("/indicators")
    public ResponseEntity<List<IndicatorDTO>> getIndicatorsList(){
        List<IndicatorDTO> indicatorDTOs = cryptoQueryService.getAllIndicators().stream()
                .map(indicator -> new IndicatorDTO(indicator.getName(), indicator.getDescription()))
                .toList();
        return ResponseEntity.ok(indicatorDTOs);
    }

    @GetMapping("/last-updated")
        public ResponseEntity<String> getOldestUpdated(){
            String formattedTimestamp = cryptoQueryService.getFormattedOldestUpdateTimestamp();
            return ResponseEntity.ok(formattedTimestamp);
        }

    // CRUD with user saved quaries - GET all,POST new one, PUT updtae one, DELETE one
    // @GetMapping("/saved-queries") // GET all
    // @GetMapping("/saved-queries") // POST create new one
    // @GetMapping("/saved-queries/{name}") // GET one by name
    // @GetMapping("/saved-queries/{name}") // PUT update one by name
    // @GetMapping("/saved-queries/{name}") // DELETE one by name
    // @GetMapping("/saved-queries") // DELETE all saved queries
    // all the actions with one entry, remember, with HATEOS!

    // Endpoint for obtaining data of a result
    // GET /cryptolytics/saved-queries/{id}/results

    //   @GetMapping("/stats")
    // for all stats , total number of currencies, total number of indicators, ...

    // 1 to N relationship - history of particular indicator (price history)
}

