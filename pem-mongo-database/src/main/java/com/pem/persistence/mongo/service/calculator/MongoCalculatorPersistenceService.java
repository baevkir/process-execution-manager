package com.pem.persistence.mongo.service.calculator;

import com.pem.model.calculator.common.CalculatorDTO;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.mongo.model.calculator.common.CalculatorEntity;
import com.pem.persistence.mongo.repository.calculator.CalculatorRepository;
import com.pem.persistence.mongo.service.common.AbstractMongoPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;

public class MongoCalculatorPersistenceService extends AbstractMongoPersistenceService<CalculatorDTO, CalculatorEntity> implements CalculatorPersistenceService {

    @Autowired
    private CalculatorRepository repository;

    @Override
    protected CalculatorRepository getRepository() {
        return repository;
    }

    @Override
    public CalculatorDTO createCalculator(CalculatorDTO operation) {
        return create(operation);
    }

    @Override
    public void updateCalculator(CalculatorDTO operation) {
        update(operation);
    }

    @Override
    public CalculatorDTO getCalculator(BigInteger id) {
        return getOne(id);
    }

    @Override
    public List<CalculatorDTO> getAllCalculators() {
        return getAll();
    }

    @Override
    public <C extends CalculatorDTO> List<C> getCalculatorsByType(final Class<C> targetClass) {
        return getAllByType(targetClass);
    }

    @Override
    public void deleteCalculator(BigInteger id) {
        delete(id);
    }
}
