package com.pem.logic.bean.provider.calculator;

import com.pem.conditioncalculator.ConditionCalculator;

import java.util.Map;

public interface ConditionCalculatorProvider {
    <C extends ConditionCalculator> C createCalculator(String beanName, Class<C> conditionCalculatorClass);
    <C extends ConditionCalculator> Map<String, C> getAllGlobalCalculators(Class<C> conditionCalculatorClass);
}
