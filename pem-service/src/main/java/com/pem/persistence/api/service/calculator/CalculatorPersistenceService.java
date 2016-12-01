package com.pem.persistence.api.service.calculator;

import com.pem.persistence.api.model.calculator.common.ConditionCalculatorObject;

import java.math.BigInteger;
import java.util.List;

public interface CalculatorPersistenceService {
    ConditionCalculatorObject createCalculator(ConditionCalculatorObject calculator);
    void updateCalculator(ConditionCalculatorObject calculator);
    ConditionCalculatorObject getCalculator(BigInteger id);
    List<ConditionCalculatorObject> getAllCalculators();
    <C extends ConditionCalculatorObject> List<C> getCalculatorsByType(final Class<C> targetClass);
    void deleteCalculator(BigInteger id);
}
