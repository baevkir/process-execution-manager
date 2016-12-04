package com.pem.logic.bean.provider.calculator;

import com.pem.core.calculator.Calculator;

import java.util.Map;

public interface ConditionCalculatorProvider {
    <C extends Calculator> C createCalculator(String beanName, Class<C> conditionCalculatorClass);
    <C extends Calculator> Map<String, C> getAllGlobalCalculators(Class<C> conditionCalculatorClass);
}
