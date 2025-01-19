package com.backendproject.cryptolytics.api.controller;

import com.backendproject.cryptolytics.domain.service.ApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cryptolytics/api-keys")
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    @Autowired
    public ApiKeyController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @PostMapping
    public ResponseEntity<String> generateApiKey(@RequestParam(defaultValue = "defaultUser") String userId) {
        String apiKey = apiKeyService.generateApiKeyForUser(userId);
        return ResponseEntity.ok(apiKey);
    }

    @DeleteMapping("/{apiKey}")
    public ResponseEntity<String> deleteApiKey(@PathVariable String apiKey) {
        boolean deleted = apiKeyService.deleteApiKey(apiKey);

        //TODO: when apiKey is deleted, all savedQueries are deleted

        if (deleted) {
            return ResponseEntity.ok("API key deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("API key not found.");
        }
    }
}
