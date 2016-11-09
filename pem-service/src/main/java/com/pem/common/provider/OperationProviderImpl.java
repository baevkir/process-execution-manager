package com.pem.common.provider;

import com.pem.common.provider.annotatin.RegisterGlobalCalculator;
import com.pem.common.provider.annotatin.RegisterGlobalOperation;
import com.pem.conditioncalculator.ConditionCalculator;
import com.pem.operation.basic.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class OperationProviderImpl implements OperationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationProviderImpl.class);
    private static final String OPERATION_BEAN_PREF = "basic-operation.";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public <O extends Operation> O createBasicOperation(Class<O> operationClass) {
        LOGGER.trace("Start to create operation {}.", operationClass);
        String beanName = getBeanName(operationClass);
        LOGGER.trace("Get Prototype bean {}.", beanName);

        return createBasicOperation(beanName, operationClass);
    }

    @Override
    public <O extends Operation> O createBasicOperation(String beanName, Class<O> operationClass) {
        Assert.isTrue(operationClass.isInterface(), String.format("Can`t create bean by Class %s. %s is not Interface.", operationClass, operationClass));
        Assert.isTrue(applicationContext.isPrototype(beanName), "Basic operation can't have scope different from 'Prototype'.");
        Operation bean = getOperation(beanName);

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
        Assert.isAssignable(ConditionCalculator.class, bean.getClass(), String.format("Bean %s is not Instance Of ConditionCalculatorService", beanName));
        return (O) bean;
    }

    @Override
    public Map<String, Operation> getAllGlobalOperations() {
        return findBeansByAnnotation(RegisterGlobalOperation.class, Operation.class);
    }

    @Override
    public Map<String, ConditionCalculator> getAllGlobalConditionCalculators() {
        return findBeansByAnnotation(RegisterGlobalCalculator.class, ConditionCalculator.class);
    }

    private <O extends Operation> String getBeanName(Class<O> operationClass) {
        String className = operationClass.getSimpleName();
        LOGGER.trace("Get class name {}.", className);
        return OPERATION_BEAN_PREF + className;
    }

    private <T> Map<String, T> findBeansByAnnotation(Class<? extends Annotation> annotation, Class<T> tClass) {
        Map<String, T> result = new HashMap<>();
        Map<String, T> beans = applicationContext.getBeansOfType(tClass, true, true);
        LOGGER.trace("Try to Find beans {} with annotation {}.", tClass, annotation);

        for (Map.Entry<String, T> entry : beans.entrySet()) {
            String key = entry.getKey();
            T value = entry.getValue();
            Class clazz = AopProxyUtils.ultimateTargetClass(value);

            if (clazz.isAnnotationPresent(annotation)) {
                result.put(key, value);
            }
        }

        return result;
    }
}
