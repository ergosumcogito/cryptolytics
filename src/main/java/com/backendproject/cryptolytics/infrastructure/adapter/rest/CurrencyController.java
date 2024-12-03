package com.backendproject.cryptolytics.infrastructure.adapter.rest;

import com.backendproject.cryptolytics.domain.model.entities.TrackedCurrency;
import com.backendproject.cryptolytics.infrastructure.adapter.persistence.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    @Autowired
    private CurrencyRepository currencyRepository;

    // GET endpoint to fetch all currencies
    @GetMapping
    public List<TrackedCurrency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    // POST endpoint to add a new currency
    @PostMapping
    public TrackedCurrency addCurrency(@RequestBody TrackedCurrency currency) {
        return currencyRepository.save(currency);
    }
}
