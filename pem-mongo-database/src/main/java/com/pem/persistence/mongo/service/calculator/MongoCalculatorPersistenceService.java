package com.pem.persistence.mongo.service.calculator;

import com.pem.model.calculator.common.CalculatorDTO;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.mongo.model.calculator.common.CalculatorEntity;
import com.pem.persistence.mongo.repository.calculator.CalculatorRepository;
import com.pem.persistence.mongo.service.common.AbstractMongoPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class MongoCalculatorPersistenceService extends AbstractMongoPersistenceService<CalculatorDTO, CalculatorEntity> implements CalculatorPersistenceService {

    @Autowired
    private CalculatorRepository repository;

    @Override
    protected CalculatorRepository getRepository() {
        return repository;
    }

    @Override
    public Mono<CalculatorDTO> createCalculator(CalculatorDTO operation) {
        return create(operation);
    }

    @Override
    public Mono<Void> updateCalculator(CalculatorDTO operation) {
        return update(operation);
    }

    @Override
    public Mono<CalculatorDTO> getCalculator(BigInteger id) {
        return getOne(id);
    }

    @Override
    public Flux<CalculatorDTO> getAllCalculators() {
        return getAll();
    }

    @Override
    public <C extends CalculatorDTO> Flux<C> getCalculatorsByType(final Class<C> targetClass) {
        return getAllByType(targetClass);
    }

    @Override
    public Mono<Void> deleteCalculator(BigInteger id) {
        return delete(id);
    }
}
