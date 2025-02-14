package com.backendproject.cryptolytics.api.controller;

import com.backendproject.cryptolytics.api.dto.SavedQueryDTO;
import com.backendproject.cryptolytics.application.service.ApiKeyService;
import com.backendproject.cryptolytics.application.service.SavedQueryService;
import com.backendproject.cryptolytics.domain.model.ApiKey;
import com.backendproject.cryptolytics.infrastructure.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cryptolytics/saved-queries")
public class SavedQueryController {

    private final SavedQueryService savedQueryService;
    private final ApiKeyService apiKeyService;

    @Autowired
    public SavedQueryController(SavedQueryService savedQueryService, ApiKeyService apiKeyService) {
        this.savedQueryService = savedQueryService;
        this.apiKeyService = apiKeyService;
    }

    @GetMapping("/{apiKey}/{queryName}")
    public ResponseEntity<?> getSavedQueryValue(@PathVariable String apiKey, @PathVariable String queryName) {
        ApiKey apiKeyModel = apiKeyService.findByApiKey(apiKey);

        if (apiKeyModel == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }

        try {
            String indicatorValue = savedQueryService.getSavedQueryIndicatorValue(queryName, apiKeyModel);
            return ResponseEntity.ok(indicatorValue);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{apiKey}")
    public ResponseEntity<?> getSavedQueriesByApiKey(@PathVariable String apiKey) {
        ApiKey apiKeyModel = apiKeyService.findByApiKey(apiKey);

        if (apiKeyModel == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }

        try {
            List<SavedQueryDTO> savedQueries = savedQueryService.getAllSavedQueriesByApiKey(apiKeyModel);
            return ResponseEntity.ok(savedQueries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching saved queries");
        }
    }

    @PostMapping("/{apiKey}/{queryName}/{currency}/{indicator}")
    public ResponseEntity<?> createSavedQuery(@PathVariable String apiKey,
                                              @PathVariable String queryName,
                                              @PathVariable String currency,
                                              @PathVariable String indicator) {
        // Validate the API key
        ApiKey apiKeyModel = apiKeyService.findByApiKey(apiKey);
        if (apiKeyModel == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }

        try {
            // Create a new saved query
            savedQueryService.createSavedQuery(apiKeyModel, queryName, currency, indicator);
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved query created successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{apiKey}/{queryName}")
    public ResponseEntity<?> deleteSavedQuery(@PathVariable String apiKey, @PathVariable String queryName) {
        ApiKey apiKeyModel = apiKeyService.findByApiKey(apiKey);

        if (apiKeyModel == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }

        try {
            savedQueryService.deleteSavedQuery(apiKeyModel, queryName);
            return ResponseEntity.ok("Saved query deleted successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting saved query");
        }
    }

    @PutMapping("/{apiKey}/{existingQueryName}/{newQueryName}/{newCurrency}/{newIndicator}")
    public ResponseEntity<?> updateSavedQuery(@PathVariable String apiKey,
                                              @PathVariable String existingQueryName,
                                              @PathVariable String newQueryName,
                                              @PathVariable String newCurrency,
                                              @PathVariable String newIndicator) {
        ApiKey apiKeyModel = apiKeyService.findByApiKey(apiKey);

        if (apiKeyModel == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }

        try {
            savedQueryService.updateSavedQuery(apiKeyModel, existingQueryName, newQueryName, newCurrency, newIndicator);
            return ResponseEntity.ok("Saved query updated successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating saved query");
        }
    }

    @DeleteMapping("/{apiKey}")
    public ResponseEntity<?> deleteAllSavedQueries(@PathVariable String apiKey) {
        ApiKey apiKeyModel = apiKeyService.findByApiKey(apiKey);
        if (apiKeyModel == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }

        try {
            savedQueryService.deleteAllSavedQueriesByApiKey(apiKeyModel);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All saved queries deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
