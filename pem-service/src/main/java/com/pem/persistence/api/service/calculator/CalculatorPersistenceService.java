package com.pem.persistence.api.service.calculator;

import com.pem.model.calculator.common.ConditionCalculatorDTO;

import java.math.BigInteger;
import java.util.List;

public interface CalculatorPersistenceService {
    ConditionCalculatorDTO createCalculator(ConditionCalculatorDTO calculator);
    void updateCalculator(ConditionCalculatorDTO calculator);
    ConditionCalculatorDTO getCalculator(BigInteger id);
    List<ConditionCalculatorDTO> getAllCalculators();
    <C extends ConditionCalculatorDTO> List<C> getCalculatorsByType(final Class<C> targetClass);
    void deleteCalculator(BigInteger id);
}
