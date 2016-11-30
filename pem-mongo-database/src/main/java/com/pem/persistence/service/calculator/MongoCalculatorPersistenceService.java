package com.pem.persistence.service.calculator;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.model.calculator.common.CalculatorEntity;
import com.pem.persistence.repository.calculator.CalculatorRepository;
import com.pem.persistence.service.common.AbstractMongoPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

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
        String className = targetClass.getCanonicalName();
        List<CalculatorEntity> operationEntities = repository.findByImplementation(className);

        return FluentIterable.from(operationEntities).transform(new Function<CalculatorEntity, C>() {
            @Override
            public C apply(CalculatorEntity input) {
                Assert.isInstanceOf(targetClass, input);
                return targetClass.cast(input);
            }
        }).toList();
    }

    @Override
    public void deleteCalculator(BigInteger id) {
        delete(id);
    }
}
