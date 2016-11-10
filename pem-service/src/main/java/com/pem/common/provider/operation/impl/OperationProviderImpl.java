package com.pem.common.provider.operation.impl;

import com.pem.common.provider.operation.OperationProvider;
import com.pem.common.utils.ApplicationContextWrapper;
import com.pem.operation.basic.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public class OperationProviderImpl implements OperationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationProviderImpl.class);
    private static final String OPERATION_BEAN_PREF = "basic-operation.";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public <O extends Operation> O createCommonOperation(Class<O> operationClass) {
        LOGGER.trace("Start to create operation {}.", operationClass);
        String beanName = getCommonOperationName(operationClass);
        LOGGER.trace("Get Prototype bean {}.", beanName);

        return createOperation(beanName, operationClass);
    }

    @Override
    public <O extends Operation> O createOperation(String beanName, Class<O> operationClass) {
        ApplicationContextWrapper wrapper = new ApplicationContextWrapper(applicationContext);
        return wrapper.getPrototypeBeanByType(beanName, operationClass);
    }

    @Override
    public Map<String, Operation> getAllGlobalOperations() {
        ApplicationContextWrapper wrapper = new ApplicationContextWrapper(applicationContext);
        return wrapper.findBeanByAnnotation(RegisterGlobalOperation.class, Operation.class);
    }

    private <O extends Operation> String getCommonOperationName(Class<O> operationClass) {
        String className = operationClass.getSimpleName();
        LOGGER.trace("Get class name {}.", className);
        return OPERATION_BEAN_PREF + className;
    }

}
