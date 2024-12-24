package com.backendproject.cryptolytics.infrastructure.adapter.rest;

import com.backendproject.cryptolytics.domain.model.entities.TrackedCurrency;
import com.backendproject.cryptolytics.infrastructure.adapter.persistence.TrackedCurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    @Autowired
    private TrackedCurrencyRepository currencyRepository;

    public CurrencyController(TrackedCurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

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
