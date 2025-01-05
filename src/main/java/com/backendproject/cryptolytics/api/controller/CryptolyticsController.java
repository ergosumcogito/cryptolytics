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

    @GetMapping("/currencies/{currency}/data/{indicator}")
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

  //  @GetMapping("/market_cap")

  //  @GetMapping("/market_cap_rank")

 //   @GetMapping("/high_24h")


 //   @GetMapping("/all_data")
 //   @GetMapping("/chart")


}

