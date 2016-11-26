package com.pem.persistence.api.service.calculator;

import com.pem.persistence.model.calculator.common.CalculatorEntity;

import java.math.BigInteger;
import java.util.List;

public interface CalculatorPersistenceService {
    CalculatorEntity createCalculator(CalculatorEntity operationEntity);
    void updateCalculator(CalculatorEntity operationEntity);
    CalculatorEntity getCalculator(BigInteger id);
    List<CalculatorEntity> getAllCalculators();
    void deleteCalculator(BigInteger id);
}
