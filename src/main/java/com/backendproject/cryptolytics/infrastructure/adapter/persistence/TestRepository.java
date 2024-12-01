package com.backendproject.cryptolytics.infrastructure.adapter.persistence;

import com.backendproject.cryptolytics.domain.model.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
