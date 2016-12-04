package com.pem.logic.service.calculator;

import com.pem.core.conditioncalculator.ConditionCalculator;
import com.pem.model.calculator.common.CalculatorDTO;
import com.pem.model.common.bean.BeanObject;

import java.math.BigInteger;
import java.util.List;

public interface ConditionCalculatorService {
    CalculatorDTO createConditionCalculator(CalculatorDTO calculatorEntity);
    void updateConditionCalculator(CalculatorDTO calculatorEntity);
    void deleteConditionCalculator(BigInteger id);
    CalculatorDTO getConditionCalculator(BigInteger id);
    List<CalculatorDTO> getAllConditionCalculators();
    <C extends ConditionCalculator> List<BeanObject> getConditionCalculatorBeanEntitiesForClass(Class<C> tClass);
}
