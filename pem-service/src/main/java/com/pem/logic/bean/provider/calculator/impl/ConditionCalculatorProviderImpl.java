package com.pem.logic.bean.provider.calculator.impl;

import com.pem.logic.bean.provider.calculator.ConditionCalculatorProvider;
import com.pem.logic.common.utils.ApplicationContextWrapper;
import com.pem.core.conditioncalculator.ConditionCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

public class ConditionCalculatorProviderImpl implements ConditionCalculatorProvider, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionCalculatorProviderImpl.class);

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
