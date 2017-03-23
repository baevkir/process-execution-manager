package com.pem.logic.service.calculator.impl;

import com.pem.logic.service.calculator.CalculatorService;
import com.pem.model.calculator.common.CalculatorDTO;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class CalculatorServiceImpl implements CalculatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorServiceImpl.class);

    private CalculatorPersistenceService persistenceService;

    @Override
    public Mono<CalculatorDTO> createCalculator(CalculatorDTO calculatorDTO) {
        LOGGER.debug("Create new Calculator: {}.", calculatorDTO);
        return persistenceService.createCalculator(calculatorDTO);
    }

    @Override
    public Mono<Void> updateCalculator(CalculatorDTO calculatorDTO) {
        LOGGER.debug("Update Calculator: {}.", calculatorDTO);
        return persistenceService.updateCalculator(calculatorDTO);
    }

    @Override
    public  Mono<Void> deleteCalculator(BigInteger id) {
        LOGGER.debug("Delete Calculator by id: {}.", id);
        return persistenceService.deleteCalculator(id);
    }

    @Override
    public Mono<CalculatorDTO> getCalculator(BigInteger id) {
        LOGGER.debug("Get Calculator by id: {}.", id);
        return persistenceService.getCalculator(id);
    }

    @Override
    public Flux<CalculatorDTO> getAllCalculators() {
        LOGGER.debug("Get All ConditionCalculators.");
        return persistenceService.getAllCalculators();
    }

    public void setPersistenceService(CalculatorPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }
}
