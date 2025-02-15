package com.backendproject.cryptolytics.api.controller;

import com.backendproject.cryptolytics.api.dto.CurrencyDTO;
import com.backendproject.cryptolytics.api.dto.HistoryEntryDTO;
import com.backendproject.cryptolytics.api.dto.IndicatorDTO;
import com.backendproject.cryptolytics.application.service.CryptoQueryService;
import com.backendproject.cryptolytics.domain.model.CurrencyIndicatorValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cryptolytics")
public class CryptolyticsController {

    private final CryptoQueryService cryptoQueryService;
    private final PagedResourcesAssembler<CurrencyDTO> pagedResourcesAssembler;

    @Autowired
    public CryptolyticsController(CryptoQueryService cryptoQueryService,
                                  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") PagedResourcesAssembler<?> pagedResourcesAssembler) {
        this.cryptoQueryService = cryptoQueryService;

        PagedResourcesAssembler<CurrencyDTO> assembler = (PagedResourcesAssembler<CurrencyDTO>) pagedResourcesAssembler;
        this.pagedResourcesAssembler = assembler;
    }

    @GetMapping("/currencies/{currency}/{indicator}")
    public ResponseEntity<Object> getIndicatorForCurrency(
            @PathVariable String currency,
            @PathVariable String indicator) {

            Object result  = cryptoQueryService.getIndicatorValue(currency, indicator);
            return ResponseEntity.ok(result);
    }

    @GetMapping("/currencies")
    public ResponseEntity<PagedModel<EntityModel<CurrencyDTO>>> getCurrenciesList(
           @PageableDefault(size = 10) Pageable pageable){
        Page<CurrencyDTO> currencyPage  = cryptoQueryService.getAllCurrencies(pageable);
        PagedModel<EntityModel<CurrencyDTO>> pagedModel = pagedResourcesAssembler.toModel(
                currencyPage, EntityModel::of);
        return ResponseEntity.ok(pagedModel);
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

}

