package com.pem.logic.bean.provider.operation.impl;

import com.pem.core.common.utils.ApplicationContextWrapper;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.bean.provider.operation.OperationProvider;
import com.pem.logic.common.ServiceConstants;
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

public class OperationProviderImpl implements OperationProvider, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationProviderImpl.class);
    private static final String OPERATION_BEAN_PREF = ServiceConstants.CUSTOM_OPERATION_BEAN_PREF;

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
    public Set<BeanObject> getAllOperationBeanObjects() {
        LOGGER.debug("Get All OperationBeanObjects .");
        ApplicationContextWrapper wrapper = new ApplicationContextWrapper(applicationContext);
        Map<String, Operation> beans = wrapper.findBeanByAnnotation(RegisterGlobalOperation.class, Operation.class);

        Set<BeanObject> operations = new HashSet<>();
        for (Map.Entry<String, Operation> entry : beans.entrySet()) {
            String beanName = entry.getKey();
            LOGGER.trace("Add bean with name {}", beanName);
            BeanObjectBuilder beanObjectBuilder = BeanObjectBuilder.newInstance().setBeanName(beanName);

            Class clazz = AopProxyUtils.ultimateTargetClass(entry.getValue());
            RegisterGlobalOperation annotation = (RegisterGlobalOperation) clazz.getAnnotation(RegisterGlobalOperation.class);
            String name = annotation.value();
            LOGGER.trace("Presentation name for bean {}", name);
            beanObjectBuilder.setName(name);

            operations.add(beanObjectBuilder.build());
        }

        return operations;
    }

    private <O extends Operation> String getCommonOperationName(Class<O> operationClass) {
        String className = operationClass.getSimpleName();
        LOGGER.trace("Get class name {}.", className);
        return OPERATION_BEAN_PREF + className;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
