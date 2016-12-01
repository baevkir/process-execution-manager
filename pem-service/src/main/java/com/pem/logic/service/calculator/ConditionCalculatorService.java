package com.pem.logic.service.calculator;

import com.pem.conditioncalculator.ConditionCalculator;
import com.pem.persistence.api.model.calculator.common.ConditionCalculatorObject;
import com.pem.persistence.api.model.common.bean.BeanObject;

import java.math.BigInteger;
import java.util.List;

public interface ConditionCalculatorService {
    ConditionCalculatorObject createConditionCalculator(ConditionCalculatorObject calculatorEntity);
    void updateConditionCalculator(ConditionCalculatorObject calculatorEntity);
    void deleteConditionCalculator(BigInteger id);
    ConditionCalculatorObject getConditionCalculator(BigInteger id);
    List<ConditionCalculatorObject> getAllConditionCalculators();
    <C extends ConditionCalculator> List<BeanObject> getConditionCalculatorBeanEntitiesForClass(Class<C> tClass);
}
