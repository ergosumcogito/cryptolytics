package com.backendproject.cryptolytics.api.controller;

import com.backendproject.cryptolytics.api.dto.CurrencyDTO;
import com.backendproject.cryptolytics.api.dto.HistoryEntryDTO;
import com.backendproject.cryptolytics.api.dto.IndicatorDTO;
import com.backendproject.cryptolytics.domain.service.CryptoQueryService;
import com.backendproject.cryptolytics.persistence.entity.CurrencyIndicatorValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

            Object result  = cryptoQueryService.getIndicatorValue(currency, indicator);
            return ResponseEntity.ok(result);
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

    @GetMapping("/currencies/{currency}/{indicator}/history")
    public ResponseEntity<Map <String, List<HistoryEntryDTO>>> getIndicatorHistory(
            @PathVariable String currency,
            @PathVariable String indicator,
             @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

            List<CurrencyIndicatorValue> history  = cryptoQueryService.getIndicatorHistory(currency, indicator, startDate, endDate);

            List<HistoryEntryDTO> historyDTOs = history.stream()
                    .map(entry -> new HistoryEntryDTO(entry.getTimestamp(), indicator.toLowerCase(),  entry.getValue()))
                    .toList();

            Map<String, List<HistoryEntryDTO>> response = Map.of("history", historyDTOs);

            return ResponseEntity.ok(response);
    }




    // CRUD with user saved queries - GET all,POST new one, PUT update one, DELETE one
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
}

