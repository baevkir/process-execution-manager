package com.pem.logic.service.calculator.impl;

import com.pem.core.calculator.Calculator;
import com.pem.model.calculator.common.CalculatorDTO;
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
    public CalculatorDTO createConditionCalculator(CalculatorDTO calculatorEntity) {
        LOGGER.debug("Create new Calculator: {}.", calculatorEntity);
        return persistenceService.createCalculator(calculatorEntity);
    }

    @Override
    public void updateConditionCalculator(CalculatorDTO calculatorEntity) {
        LOGGER.debug("Update Calculator: {}.", calculatorEntity);
        persistenceService.updateCalculator(calculatorEntity);
    }

    @Override
    public void deleteConditionCalculator(BigInteger id) {
        LOGGER.debug("Delete Calculator by id: {}.", id);
        persistenceService.deleteCalculator(id);
    }

    @Override
    public CalculatorDTO getConditionCalculator(BigInteger id) {
        LOGGER.debug("Get Calculator by id: {}.", id);
        return persistenceService.getCalculator(id);
    }

    @Override
    public List<CalculatorDTO> getAllConditionCalculators() {
        LOGGER.debug("Get All ConditionCalculators.");
        return persistenceService.getAllCalculators();
    }

    @Override
    public <C extends Calculator> List<BeanObject> getConditionCalculatorBeanEntitiesForClass(Class<C> tClass) {
        return beanEntityProvider.provideCalculatorBeanEntities(tClass);
    }

    public void setPersistenceService(CalculatorPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public void setBeanEntityProvider(CalculatorBeanEntityProvider beanEntityProvider) {
        this.beanEntityProvider = beanEntityProvider;
    }
}
