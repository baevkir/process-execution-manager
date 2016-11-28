package com.pem.logic.service.calculator;

import com.pem.conditioncalculator.ConditionCalculator;
import com.pem.persistence.model.calculator.common.CalculatorEntity;
import com.pem.persistence.model.common.bean.BeanEntity;

import java.math.BigInteger;
import java.util.List;

public interface ConditionCalculatorService {
    CalculatorEntity createConditionCalculator(CalculatorEntity calculatorEntity);
    void updateConditionCalculator(CalculatorEntity calculatorEntity);
    void deleteConditionCalculator(BigInteger id);
    CalculatorEntity getConditionCalculator(BigInteger id);
    List<CalculatorEntity> getAllConditionCalculators();
    <C extends ConditionCalculator> List<BeanEntity> getConditionCalculatorBeanEntitiesForClass(Class<C> tClass);
}
