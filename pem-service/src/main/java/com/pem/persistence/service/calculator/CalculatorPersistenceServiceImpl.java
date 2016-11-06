package com.pem.persistence.service.calculator;

import com.pem.persistence.model.calculator.common.CalculatorEntity;
import com.pem.persistence.repository.calculator.CalculatorRepository;
import com.pem.persistence.service.common.AbstractPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;

public class CalculatorPersistenceServiceImpl extends AbstractPersistenceService<CalculatorEntity, CalculatorRepository> implements CalculatorPersistenceService {

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
    public void deleteCalculator(BigInteger id) {
        delete(id);
    }
}
