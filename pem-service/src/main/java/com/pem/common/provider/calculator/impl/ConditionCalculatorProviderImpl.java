package com.pem.common.provider.calculator.impl;

import com.pem.common.provider.calculator.ConditionCalculatorProvider;
import com.pem.common.utils.ApplicationContextWrapper;
import com.pem.conditioncalculator.ConditionCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public class ConditionCalculatorProviderImpl implements ConditionCalculatorProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionCalculatorProviderImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public <C extends ConditionCalculator> C createCalculator(String beanName, Class<C> conditionCalculatorClass) {
        ApplicationContextWrapper wrapper = new ApplicationContextWrapper(applicationContext);
        return wrapper.getPrototypeBeanByType(beanName, conditionCalculatorClass);
    }


    @Override
    public <C extends ConditionCalculator> Map<String, C> getAllGlobalCalculators(Class<C> conditionCalculatorClass) {
        ApplicationContextWrapper wrapper = new ApplicationContextWrapper(applicationContext);
        return wrapper.findBeanByAnnotation(RegisterGlobalCalculator.class, conditionCalculatorClass);
    }

}
