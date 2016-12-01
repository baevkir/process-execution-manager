package com.pem.logic.service.calculator.impl;

import com.pem.conditioncalculator.ConditionCalculator;
import com.pem.model.calculator.common.ConditionCalculatorDTO;
import com.pem.model.common.bean.BeanObject;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.logic.service.calculator.ConditionCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;

public class ConditionCalculatorServiceImpl implements ConditionCalculatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionCalculatorServiceImpl.class);

    private CalculatorPersistenceService persistenceService;

    private CalculatorBeanEntityProvider beanEntityProvider;

    @Override
    public ConditionCalculatorDTO createConditionCalculator(ConditionCalculatorDTO calculatorEntity) {
        LOGGER.debug("Create new ConditionCalculator: {}.", calculatorEntity);
        return persistenceService.createCalculator(calculatorEntity);
    }

    @Override
    public void updateConditionCalculator(ConditionCalculatorDTO calculatorEntity) {
        LOGGER.debug("Update ConditionCalculator: {}.", calculatorEntity);
        persistenceService.updateCalculator(calculatorEntity);
    }

    @Override
    public void deleteConditionCalculator(BigInteger id) {
        LOGGER.debug("Delete ConditionCalculator by id: {}.", id);
        persistenceService.deleteCalculator(id);
    }

    @Override
    public ConditionCalculatorDTO getConditionCalculator(BigInteger id) {
        LOGGER.debug("Get ConditionCalculator by id: {}.", id);
        return persistenceService.getCalculator(id);
    }

    @Override
    public List<ConditionCalculatorDTO> getAllConditionCalculators() {
        LOGGER.debug("Get All ConditionCalculators.");
        return persistenceService.getAllCalculators();
    }

    @Override
    public <C extends ConditionCalculator> List<BeanObject> getConditionCalculatorBeanEntitiesForClass(Class<C> tClass) {
        return beanEntityProvider.provideCalculatorBeanEntities(tClass);
    }

    public void setPersistenceService(CalculatorPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public void setBeanEntityProvider(CalculatorBeanEntityProvider beanEntityProvider) {
        this.beanEntityProvider = beanEntityProvider;
    }
}
