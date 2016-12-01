package com.pem.logic.service.calculator.impl;

import com.pem.conditioncalculator.ConditionCalculator;
import com.pem.persistence.api.model.common.bean.BeanObject;

import java.util.List;

public interface CalculatorBeanEntityProvider {
    <C extends ConditionCalculator> List<BeanObject> provideCalculatorBeanEntities(Class<C> calculatorClass);
}
