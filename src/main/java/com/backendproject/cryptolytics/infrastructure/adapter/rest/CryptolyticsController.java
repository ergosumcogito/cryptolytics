package com.backendproject.cryptolytics.infrastructure.adapter.rest;

import com.backendproject.cryptolytics.application.dto.CurrencyDTO;
import com.backendproject.cryptolytics.domain.service.CryptoQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

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

    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyDTO>> getCurrenciesList(){
        List<CurrencyDTO> currencyDTOs  = cryptoQueryService.getAllCurrencies().stream()
                .map(currency -> new CurrencyDTO(currency.getSymbol(), currency.getName()))
                .toList();
        return ResponseEntity.ok(currencyDTOs);
    }

  //  @GetMapping("/market_cap")

  //  @GetMapping("/market_cap_rank")

 //   @GetMapping("/high_24h")

 // @GetMapping("/last_updated")

 //   @GetMapping("/all_data")
 //   @GetMapping("/chart")


}

