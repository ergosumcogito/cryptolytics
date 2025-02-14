package com.backendproject.cryptolytics.infrastructure.persistence.adapter;

import com.backendproject.cryptolytics.domain.model.Indicator;
import com.backendproject.cryptolytics.domain.port.out.IndicatorRepository;
import com.backendproject.cryptolytics.infrastructure.persistence.mapper.IndicatorMapper;
import com.backendproject.cryptolytics.infrastructure.persistence.repository.IndicatorEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IndicatorRepositoryAdapter implements IndicatorRepository {

    private final IndicatorEntityRepository indicatorEntityRepository;
    private final IndicatorMapper indicatorMapper;

    @Override
    public Optional<Indicator> findByName(String name) {
        return indicatorEntityRepository.findByName(name)
                .map(indicatorMapper::toDomain);
    }

    @Override
    public List<Indicator> findAll() {
        return indicatorEntityRepository.findAll()
                .stream().map(indicatorMapper::toDomain).toList();
    }
}
