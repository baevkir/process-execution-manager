package com.pem.basic.provider;

import com.pem.conditioncalculator.ConditionCalculator;
import com.pem.operation.basic.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

public class OperationProviderImpl implements OperationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationProviderImpl.class);
    private static final String OPERATION_BEAN_PREF = "basic-operation.";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public <O extends Operation> O createBasicOperation(Class<O> operationClass) {
        Assert.isTrue(operationClass.isInterface(), String.format("Can`t create bean by Class %s. %s is not Interface.", operationClass, operationClass));
        LOGGER.trace("Start to create operation {}.", operationClass);
        String beanName = getBeanName(operationClass);
        LOGGER.trace("Get Prototype bean {}.", beanName);

        return createBasicOperation(beanName, operationClass);
    }

    @Override
    public <O extends Operation> O createBasicOperation(String beanName, Class<O> operationClass) {
        Assert.isTrue(applicationContext.isPrototype(beanName), "Basic operation can't have scope different from 'Prototype'.");
        Object bean = getOperation(beanName);
        Assert.isAssignable(operationClass, bean.getClass(), String.format("Bean %s is not Instance Of %s", beanName, operationClass));
        return operationClass.cast(bean);
    }

    @Override
    public <O extends Operation> O getOperation(String beanName) {
        Object bean = applicationContext.getBean(beanName);
        Assert.isAssignable(Operation.class, bean.getClass(), String.format("Bean %s is not Instance Of Operation", beanName));
        return (O) bean;
    }

    @Override
    public <O extends ConditionCalculator> O getConditionCalculator(String beanName) {
        Object bean = applicationContext.getBean(beanName);
        Assert.isAssignable(ConditionCalculator.class, bean.getClass(), String.format("Bean %s is not Instance Of ConditionCalculator", beanName));
        return (O) bean;
    }

    private <O extends Operation> String getBeanName(Class<O> operationClass) {
        String className = operationClass.getSimpleName();
        LOGGER.trace("Get class name {}.", className);
        return OPERATION_BEAN_PREF + className;
    }
}
