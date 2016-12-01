package com.pem.logic.service.calculator;

import com.pem.conditioncalculator.ConditionCalculator;
import com.pem.model.calculator.common.ConditionCalculatorDTO;
import com.pem.model.common.bean.BeanObject;

import java.math.BigInteger;
import java.util.List;

public interface ConditionCalculatorService {
    ConditionCalculatorDTO createConditionCalculator(ConditionCalculatorDTO calculatorEntity);
    void updateConditionCalculator(ConditionCalculatorDTO calculatorEntity);
    void deleteConditionCalculator(BigInteger id);
    ConditionCalculatorDTO getConditionCalculator(BigInteger id);
    List<ConditionCalculatorDTO> getAllConditionCalculators();
    <C extends ConditionCalculator> List<BeanObject> getConditionCalculatorBeanEntitiesForClass(Class<C> tClass);
}
