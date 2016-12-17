package com.pem.logic.bean.synchronizer.calculator;

import com.pem.logic.bean.provider.calculator.CalculatorProvider;
import com.pem.core.common.event.LaunchEventHandler;
import com.pem.logic.bean.synchronizer.operation.BeanOperationDatabaseSynchronizer;
import com.pem.model.calculator.bean.BeanCalculatorDTO;
import com.pem.core.common.bean.BeanObject;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CalculatorDatabaseSynchronizer implements LaunchEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanOperationDatabaseSynchronizer.class);

    private CalculatorProvider calculatorProvider;
    private CalculatorPersistenceService calculatorPersistenceService;

    protected void createCalculator(BeanCalculatorDTO calculator, BeanObject beanObject) {
        LOGGER.debug("Create new calculator for {}.", beanObject);
        calculator.setActive(true);
        calculator.setName(beanObject.getName());
        calculator.setBean(beanObject);

        getCalculatorPersistenceService().createCalculator(calculator);
    }

    protected void updateStatus(BeanCalculatorDTO calculator, boolean active){
        LOGGER.debug("Update status for calculator: {}.", calculator);
        if (active = calculator.isActive()) {
            return;
        }
        calculator.setActive(active);
        calculatorPersistenceService.updateCalculator(calculator);
    }

    protected CalculatorProvider getCalculatorProvider() {
        return calculatorProvider;
    }

    protected CalculatorPersistenceService getCalculatorPersistenceService() {
        return calculatorPersistenceService;
    }

    public void setCalculatorProvider(CalculatorProvider calculatorProvider) {
        this.calculatorProvider = calculatorProvider;
    }

    public void setCalculatorPersistenceService(CalculatorPersistenceService calculatorPersistenceService) {
        this.calculatorPersistenceService = calculatorPersistenceService;
    }
}
