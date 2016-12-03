package com.pem.logic.service.calculator.impl;

import com.pem.core.conditioncalculator.ConditionCalculator;
import com.pem.model.common.bean.BeanObject;

import java.util.List;

public interface CalculatorBeanEntityProvider {
    <C extends ConditionCalculator> List<BeanObject> provideCalculatorBeanEntities(Class<C> calculatorClass);
}
