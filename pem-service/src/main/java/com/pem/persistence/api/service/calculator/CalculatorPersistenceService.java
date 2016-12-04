package com.pem.persistence.api.service.calculator;

import com.pem.model.calculator.common.CalculatorDTO;

import java.math.BigInteger;
import java.util.List;

public interface CalculatorPersistenceService {
    CalculatorDTO createCalculator(CalculatorDTO calculator);
    void updateCalculator(CalculatorDTO calculator);
    CalculatorDTO getCalculator(BigInteger id);
    List<CalculatorDTO> getAllCalculators();
    <C extends CalculatorDTO> List<C> getCalculatorsByType(final Class<C> targetClass);
    void deleteCalculator(BigInteger id);
}
