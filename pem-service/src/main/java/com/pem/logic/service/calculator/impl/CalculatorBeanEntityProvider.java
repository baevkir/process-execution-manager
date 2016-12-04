package com.pem.logic.service.calculator.impl;

import com.pem.core.calculator.Calculator;
import com.pem.model.common.bean.BeanObject;

import java.util.List;

public interface CalculatorBeanEntityProvider {
    <C extends Calculator> List<BeanObject> provideCalculatorBeanEntities(Class<C> calculatorClass);
}
