package com.backendproject.cryptolytics.api.controller;

import com.backendproject.cryptolytics.api.dto.SavedQueryDTO;
import com.backendproject.cryptolytics.domain.service.ApiKeyService;
import com.backendproject.cryptolytics.domain.service.SavedQueryService;
import com.backendproject.cryptolytics.persistence.entity.*;
import jakarta.persistence.EntityNotFoundException;
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
        ApiKey apiKeyEntity = apiKeyService.findByApiKey(apiKey);

        if (apiKeyEntity == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }

        try {
            String indicatorValue = savedQueryService.getSavedQueryIndicatorValue(queryName, apiKeyEntity);
            return ResponseEntity.ok(indicatorValue);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{apiKey}")
    public ResponseEntity<?> getSavedQueriesByApiKey(@PathVariable String apiKey) {
        ApiKey apiKeyEntity = apiKeyService.findByApiKey(apiKey);

        if (apiKeyEntity == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }

        try {
            List<SavedQueryDTO> savedQueries = savedQueryService.getAllSavedQueriesByApiKey(apiKeyEntity);
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
        ApiKey apiKeyEntity = apiKeyService.findByApiKey(apiKey);
        if (apiKeyEntity == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }

        try {
            // Create a new saved query
            savedQueryService.createSavedQuery(apiKeyEntity, queryName, currency, indicator);
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved query created successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{apiKey}/{queryName}")
    public ResponseEntity<?> deleteSavedQuery(@PathVariable String apiKey, @PathVariable String queryName) {
        ApiKey apiKeyEntity = apiKeyService.findByApiKey(apiKey);

        if (apiKeyEntity == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }

        try {
            savedQueryService.deleteSavedQuery(apiKeyEntity, queryName);
            return ResponseEntity.ok("Saved query deleted successfully");
        } catch (EntityNotFoundException e) {
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
        ApiKey apiKeyEntity = apiKeyService.findByApiKey(apiKey);

        if (apiKeyEntity == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }

        try {
            savedQueryService.updateSavedQuery(apiKeyEntity, existingQueryName, newQueryName, newCurrency, newIndicator);
            return ResponseEntity.ok("Saved query updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating saved query");
        }
    }

    // @GetMapping("/saved-queries") // DELETE all saved queries
}
