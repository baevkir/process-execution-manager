package com.pem.logic.bean.provider.calculator.impl;

import com.pem.core.calculator.Calculator;
import com.pem.core.common.utils.ApplicationContextWrapper;
import com.pem.logic.bean.provider.calculator.CalculatorProvider;
import com.pem.model.common.bean.BeanObject;
import com.pem.model.common.bean.BeanObjectBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CalculatorProviderImpl implements CalculatorProvider, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorProviderImpl.class);

    private ApplicationContext applicationContext;

    @Override
    public <C extends Calculator> C createCalculator(String beanName, Class<C> calculatorClass) {
        ApplicationContextWrapper wrapper = new ApplicationContextWrapper(applicationContext);
        return wrapper.getPrototypeBeanByType(beanName, calculatorClass);
    }


    @Override
    public <C extends Calculator> Set<BeanObject> getAllCalculatorBeanObjects(Class<C> conditionCalculatorClass) {
        ApplicationContextWrapper wrapper = new ApplicationContextWrapper(applicationContext);
        Map<String, C> beans = wrapper.findBeanByAnnotation(RegisterGlobalCalculator.class, conditionCalculatorClass);

        Set<BeanObject> calculators = new HashSet<>();
        for (Map.Entry<String, C> entry : beans.entrySet()) {
            String beanName = entry.getKey();
            LOGGER.trace("Add calculator with name {}", beanName);
            BeanObjectBuilder beanObjectBuilder = BeanObjectBuilder.newInstance().setBeanName(beanName);

            Class clazz = AopProxyUtils.ultimateTargetClass(entry.getValue());
            RegisterGlobalCalculator annotation = (RegisterGlobalCalculator) clazz.getAnnotation(RegisterGlobalCalculator.class);
            String name = annotation.value();

            beanObjectBuilder.setName(name);
            LOGGER.trace("Presentation name for calculator {}", name);

            calculators.add(beanObjectBuilder.build());
        }

        return calculators;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
