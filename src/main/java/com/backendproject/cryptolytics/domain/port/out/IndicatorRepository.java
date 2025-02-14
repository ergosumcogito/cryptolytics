package com.backendproject.cryptolytics.domain.port.out;

import com.backendproject.cryptolytics.domain.model.Indicator;

import java.util.List;
import java.util.Optional;

public interface IndicatorRepository {
    Optional<Indicator> findByName(String name);
    List<Indicator> findAll();
}
