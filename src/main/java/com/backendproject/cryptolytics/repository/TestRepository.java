package com.backendproject.cryptolytics.repository;

import com.backendproject.cryptolytics.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
