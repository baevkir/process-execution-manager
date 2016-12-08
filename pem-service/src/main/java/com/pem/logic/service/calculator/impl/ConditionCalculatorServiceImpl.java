package com.pem.logic.service.calculator.impl;

import com.pem.logic.service.calculator.ConditionCalculatorService;
import com.pem.model.calculator.common.CalculatorDTO;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;

public class ConditionCalculatorServiceImpl implements ConditionCalculatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionCalculatorServiceImpl.class);

    private CalculatorPersistenceService persistenceService;

    @Override
    public CalculatorDTO createCalculator(CalculatorDTO calculatorDTO) {
        LOGGER.debug("Create new Calculator: {}.", calculatorDTO);
        return persistenceService.createCalculator(calculatorDTO);
    }

    @Override
    public void updateCalculator(CalculatorDTO calculatorDTO) {
        LOGGER.debug("Update Calculator: {}.", calculatorDTO);
        persistenceService.updateCalculator(calculatorDTO);
    }

    @Override
    public void deleteCalculator(BigInteger id) {
        LOGGER.debug("Delete Calculator by id: {}.", id);
        persistenceService.deleteCalculator(id);
    }

    @Override
    public CalculatorDTO getCalculator(BigInteger id) {
        LOGGER.debug("Get Calculator by id: {}.", id);
        return persistenceService.getCalculator(id);
    }

    @Override
    public List<CalculatorDTO> getAllCalculators() {
        LOGGER.debug("Get All ConditionCalculators.");
        return persistenceService.getAllCalculators();
    }

    public void setPersistenceService(CalculatorPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }
}
