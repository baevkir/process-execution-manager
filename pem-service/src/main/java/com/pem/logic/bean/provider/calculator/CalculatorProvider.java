package com.pem.logic.bean.provider.calculator;

import com.pem.core.calculator.Calculator;
import com.pem.core.common.bean.BeanObject;

import java.util.Set;

public interface CalculatorProvider {
    <C extends Calculator> C createCalculator(String beanName, Class<C> calculatorClass);
    <C extends Calculator> Set<BeanObject> getAllCalculatorBeanObjects(Class<C> calculatorClass);
}
