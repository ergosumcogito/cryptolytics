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




    // CRUD with user saved queries - GET all,POST new one, PUT update one, DELETE one
    // @GetMapping("/saved-queries") // GET all
    // @GetMapping("/saved-queries") // POST create new one
    // @GetMapping("/saved-queries/{name}") // GET one by name
    // @GetMapping("/saved-queries/{name}") // PUT update one by name
    // @GetMapping("/saved-queries/{name}") // DELETE one by name
    // @GetMapping("/saved-queries") // DELETE all saved queries
    // /saved-queries/{name}/details - properties of a saved query

}
