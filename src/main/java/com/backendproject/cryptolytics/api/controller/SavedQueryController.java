package com.backendproject.cryptolytics.api.controller;

import com.backendproject.cryptolytics.domain.service.ApiKeyService;
import com.backendproject.cryptolytics.domain.service.SavedQueryService;
import com.backendproject.cryptolytics.persistence.entity.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
