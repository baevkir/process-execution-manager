package com.pem.service.calculator.impl;

import com.pem.conditioncalculator.ConditionCalculator;
import com.pem.persistence.model.calculator.common.CalculatorEntity;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.service.calculator.ConditionCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;

public class ConditionCalculatorServiceImpl implements ConditionCalculatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionCalculatorServiceImpl.class);

    private CalculatorPersistenceService persistenceService;

    private CalculatorBeanEntityProvider beanEntityProvider;

    @Override
    public CalculatorEntity createConditionCalculator(CalculatorEntity calculatorEntity) {
        LOGGER.debug("Create new ConditionCalculator: {}.", calculatorEntity);
        return persistenceService.createCalculator(calculatorEntity);
    }

    @Override
    public void updateConditionCalculator(CalculatorEntity calculatorEntity) {
        LOGGER.debug("Update ConditionCalculator: {}.", calculatorEntity);
        persistenceService.updateCalculator(calculatorEntity);
    }

    @Override
    public void deleteConditionCalculator(BigInteger id) {
        LOGGER.debug("Delete ConditionCalculator by id: {}.", id);
        persistenceService.deleteCalculator(id);
    }

    @Override
    public CalculatorEntity getConditionCalculator(BigInteger id) {
        LOGGER.debug("Get ConditionCalculator by id: {}.", id);
        return persistenceService.getCalculator(id);
    }

    @Override
    public List<CalculatorEntity> getAllConditionCalculators() {
        LOGGER.debug("Get All ConditionCalculators.");
        return persistenceService.getAllCalculators();
    }

    @Override
    public <C extends ConditionCalculator> List<BeanEntity> getConditionCalculatorBeanEntitiesForClass(Class<C> tClass) {
        return beanEntityProvider.provideCalculatorBeanEntities(tClass);
    }

    public void setPersistenceService(CalculatorPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public void setBeanEntityProvider(CalculatorBeanEntityProvider beanEntityProvider) {
        this.beanEntityProvider = beanEntityProvider;
    }
}
