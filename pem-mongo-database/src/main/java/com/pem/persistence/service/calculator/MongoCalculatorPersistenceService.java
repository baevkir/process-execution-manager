package com.pem.persistence.service.calculator;

import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.model.calculator.common.CalculatorEntity;
import com.pem.persistence.repository.calculator.CalculatorRepository;
import com.pem.persistence.service.common.AbstractMongoPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;

public class MongoCalculatorPersistenceService extends AbstractMongoPersistenceService<CalculatorEntity, CalculatorRepository> implements CalculatorPersistenceService {

    @Autowired
    private CalculatorRepository repository;

    @Override
    protected CalculatorRepository getRepository() {
        return repository;
    }

    @Override
    public CalculatorEntity createCalculator(CalculatorEntity operationEntity) {
        return create(operationEntity);
    }

    @Override
    public void updateCalculator(CalculatorEntity operationEntity) {
        update(operationEntity);
    }

    @Override
    public CalculatorEntity getCalculator(BigInteger id) {
        return getOne(id);
    }

    @Override
    public List<CalculatorEntity> getAllCalculators() {
        return getAll();
    }

    @Override
    public <C extends CalculatorEntity> List<C> getCalculatorsByType(final Class<C> targetClass) {
        return getAllByType(targetClass);
    }

    @Override
    public void deleteCalculator(BigInteger id) {
        delete(id);
    }
}
