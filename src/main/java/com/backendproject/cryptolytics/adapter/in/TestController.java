package com.backendproject.cryptolytics.adapter.in;

import com.backendproject.cryptolytics.domain.model.TestEntity;
import com.backendproject.cryptolytics.adapter.out.TestRepository;
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