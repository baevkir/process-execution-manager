package com.pem.persistence.service.calculator;

import com.pem.persistence.model.calculator.common.CalculatorEntity;
import com.pem.persistence.model.common.bean.BeanEntity;

import java.math.BigInteger;
import java.util.List;

public interface ConditionCalculatorService {
    CalculatorEntity createOperation(CalculatorEntity calculatorEntity);
    void updateOperation(CalculatorEntity calculatorEntity);
    CalculatorEntity getOperation(BigInteger id);
    List<CalculatorEntity> getAllOperation();
    List<BeanEntity> getAllOperationBeanEntities();
}
