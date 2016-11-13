package com.pem.service.calculator.impl;

import com.pem.conditioncalculator.ConditionCalculator;
import com.pem.persistence.model.common.bean.BeanEntity;

import java.util.List;

public interface CalculatorBeanEntityProvider {
    <C extends ConditionCalculator> List<BeanEntity> getCalculatorBeanEntity(Class<C> calculatorClass);
}
