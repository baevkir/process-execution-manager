package com.pem.service.calculator.impl;

import com.pem.common.provider.calculator.ConditionCalculatorProvider;
import com.pem.common.provider.calculator.impl.RegisterGlobalCalculator;
import com.pem.conditioncalculator.ConditionCalculator;
import com.pem.persistence.model.common.bean.BeanEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculatorBeanEntityProviderImpl implements CalculatorBeanEntityProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorBeanEntityProviderImpl.class);

    private ConditionCalculatorProvider calculatorProvider;

    public void setCalculatorProvider(ConditionCalculatorProvider calculatorProvider) {
        this.calculatorProvider = calculatorProvider;
    }

    @Override
    public <C extends ConditionCalculator> List<BeanEntity> provideCalculatorBeanEntities(Class<C>  calculatorClass) {
        LOGGER.debug("Get All ConditionCalculatorBeanEntities for class {}.", calculatorClass);
        List<BeanEntity> calculators = new ArrayList<>();

        Map<String, C> beans = calculatorProvider.getAllGlobalCalculators(calculatorClass);
        for (Map.Entry<String, C> entry : beans.entrySet()) {
            BeanEntity calculator = new BeanEntity();
            String beanName = entry.getKey();
            LOGGER.trace("Add bean with name {}", beanName);
            calculator.setBeanName(beanName);

            Class clazz = AopProxyUtils.ultimateTargetClass(entry.getValue());
            RegisterGlobalCalculator annotation = (RegisterGlobalCalculator) clazz.getAnnotation(RegisterGlobalCalculator.class);
            String name = annotation.value();
            LOGGER.trace("Presentation name for bean {}", name);
            calculator.setName(name);

            calculators.add(calculator);
        }

        return calculators;
    }
}
