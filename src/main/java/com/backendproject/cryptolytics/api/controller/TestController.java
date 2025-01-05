package com.backendproject.cryptolytics.api.controller;

import com.backendproject.cryptolytics.persistence.entity.TestEntity;
import com.backendproject.cryptolytics.persistence.repository.TestRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private final TestRepository repository;

    public TestController(TestRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TestEntity> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public TestEntity create(@RequestBody TestEntity entity) {
        return repository.save(entity);
    }
}