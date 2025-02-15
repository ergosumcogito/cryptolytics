package com.backendproject.cryptolytics.api.controller;

import com.backendproject.cryptolytics.api.dto.CurrencyDTO;
import com.backendproject.cryptolytics.api.dto.HistoryEntryDTO;
import com.backendproject.cryptolytics.api.dto.IndicatorDTO;
import com.backendproject.cryptolytics.api.dto.IndicatorValueDTO;
import com.backendproject.cryptolytics.application.service.CryptoQueryService;
import com.backendproject.cryptolytics.domain.model.CurrencyIndicatorValue;
import com.backendproject.cryptolytics.domain.model.Indicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cryptolytics")
public class CryptolyticsController {

    private final CryptoQueryService cryptoQueryService;
    private final PagedResourcesAssembler<CurrencyDTO> pagedResourcesAssembler;
    private final PagedResourcesAssembler<HistoryEntryDTO> historyPagedResourcesAssembler;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public CryptolyticsController(CryptoQueryService cryptoQueryService,
                                  PagedResourcesAssembler<CurrencyDTO> pagedResourcesAssembler,
                                  PagedResourcesAssembler<HistoryEntryDTO> historyPagedResourcesAssembler) {
        this.cryptoQueryService = cryptoQueryService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.historyPagedResourcesAssembler = historyPagedResourcesAssembler;
    }

    @GetMapping("/currencies/{currency}/{indicator}")
    public ResponseEntity<EntityModel<IndicatorValueDTO>> getIndicatorForCurrency(
            @PathVariable String currency,
            @PathVariable String indicator) {

            Object result  = cryptoQueryService.getIndicatorValue(currency, indicator);

            IndicatorValueDTO dto = new IndicatorValueDTO(currency, indicator, result);
            EntityModel<IndicatorValueDTO> resource = EntityModel.of(dto);

        resource.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(CryptolyticsController.class)
                                .getIndicatorForCurrency(currency, indicator))
                .withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(CryptolyticsController.class)
                                .getIndicatorHistory(currency, indicator,null,null, Pageable.unpaged()))
                .withRel("history"));
        resource.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(CryptolyticsController.class)
                                .getCurrenciesList(Pageable.unpaged()))
                .withRel("currencies"));

            return ResponseEntity.ok(resource);
    }

    @GetMapping("/currencies")
    public ResponseEntity<PagedModel<EntityModel<CurrencyDTO>>> getCurrenciesList(Pageable pageable) {
        Page<CurrencyDTO> currencyPage = cryptoQueryService.getAllCurrencies(pageable);
        List<Indicator> indicators = cryptoQueryService.getAllIndicators();

        PagedModel<EntityModel<CurrencyDTO>> pagedModel = pagedResourcesAssembler.toModel(
                currencyPage,
                currencyDTO -> {
                    EntityModel<CurrencyDTO> entityModel = EntityModel.of(currencyDTO);

                    // Dynamically generate a hyperlink for each indicator
                    for (Indicator indicator : indicators) {
                        entityModel.add(WebMvcLinkBuilder.linkTo(
                                        WebMvcLinkBuilder.methodOn(CryptolyticsController.class)
                                                .getIndicatorForCurrency(currencyDTO.getSymbol(), indicator.getName()))
                                .withRel(indicator.getName()));
                    }
                    return entityModel;
                }
        );

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/indicators")
    public ResponseEntity<CollectionModel<EntityModel<IndicatorDTO>>> getIndicatorsList() {
        List<IndicatorDTO> indicatorDTOs = cryptoQueryService.getAllIndicators().stream()
                .map(indicator -> new IndicatorDTO(indicator.getName(), indicator.getDescription()))
                .toList();

        List<EntityModel<IndicatorDTO>> indicatorResources = indicatorDTOs.stream()
                .map(dto -> EntityModel.of(dto,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CryptolyticsController.class)
                                .getIndicatorsList()).withSelfRel()))
                .toList();
        CollectionModel<EntityModel<IndicatorDTO>> collectionModel = CollectionModel.of(indicatorResources);
        collectionModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CryptolyticsController.class)
                        .getIndicatorsList()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/last-updated")
    public ResponseEntity<EntityModel<String>> getOldestUpdated() {
        String formattedTimestamp = cryptoQueryService.getFormattedOldestUpdateTimestamp();

        EntityModel<String> resource = EntityModel.of(formattedTimestamp);
        resource.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CryptolyticsController.class)
                        .getOldestUpdated()).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    @GetMapping("/currencies/{currency}/{indicator}/history")
    public ResponseEntity<PagedModel<EntityModel<HistoryEntryDTO>>> getIndicatorHistory(
            @PathVariable String currency,
            @PathVariable String indicator,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(size = 5) Pageable pageable) {

        Page<CurrencyIndicatorValue> history = cryptoQueryService.getIndicatorHistory(currency, indicator, startDate, endDate, pageable);
        Page<HistoryEntryDTO> historyDTOPage = history.map(entry -> new HistoryEntryDTO(entry.getTimestamp(), indicator.toLowerCase(), entry.getValue()));
        PagedModel<EntityModel<HistoryEntryDTO>> pagedModel = historyPagedResourcesAssembler.toModel(
                historyDTOPage,
                historyDTO -> EntityModel.of(historyDTO,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CryptolyticsController.class)
                                        .getIndicatorHistory(currency, indicator, startDate, endDate, Pageable.unpaged()))
                                .withSelfRel())
        );
        return ResponseEntity.ok(pagedModel);
    }
}

