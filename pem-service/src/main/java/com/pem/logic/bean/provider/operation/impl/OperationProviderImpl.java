package com.pem.logic.bean.provider.operation.impl;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.bean.BeanObjectBuilder;
import com.pem.core.common.bean.BeansStream;
import com.pem.core.common.utils.ApplicationContextWrapper;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.bean.provider.operation.OperationProvider;
import com.pem.logic.common.ServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OperationProviderImpl implements OperationProvider, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationProviderImpl.class);
    private static final String OPERATION_BEAN_PREF = ServiceConstants.CUSTOM_OPERATION_BEAN_PREF;

    private ApplicationContextWrapper contextWrapper;

    @Override
    public <O extends Operation> O createCommonOperation(Class<O> operationClass) {
        LOGGER.trace("Start to create operation {}.", operationClass);
        String beanName = getCommonOperationName(operationClass);
        LOGGER.trace("Get Prototype bean {}.", beanName);

        return createOperation(beanName, operationClass);
    }

    @Override
    public <O extends Operation> O createOperation(String beanName, Class<O> operationClass) {
        return contextWrapper.getPrototypeBeanByType(beanName, operationClass);
    }

    @Override
    public Set<BeanObject> getAllOperationBeanObjects() {
        LOGGER.debug("Get All OperationBeanObjects .");
        final String applicationId = contextWrapper.getApplicationContext().getId();
        Map<String, Operation> beans = contextWrapper.findBeansByAnnotation(GlobalOperation.class, Operation.class);

        return BeansStream.fromBeans(beans)
                .filterWithAnnotation(GlobalOperation.class, annotation -> checkAnnotation(annotation, applicationId))
                .transform(operationEntry -> transformToBeanObject(operationEntry))
                .collect(Collectors.toSet());
    }

    private boolean checkAnnotation(GlobalOperation annotation, String applicationId) {
        if (annotation.all()) {
            return true;
        }
        List<String> executors = Arrays.asList(annotation.executors());
        return executors.contains(applicationId);
    }

    private BeanObject transformToBeanObject(BeansStream.Entry<Operation> operationEntry) {
        return BeanObjectBuilder.newInstance()
                .setBeanName(operationEntry.getBeanName())
                .setName(operationEntry.getBeanAnnotation(GlobalOperation.class).get().value())
                .build();
    }

    private <O extends Operation> String getCommonOperationName(Class<O> operationClass) {
        String className = operationClass.getSimpleName();
        LOGGER.trace("Get class name {}.", className);
        return OPERATION_BEAN_PREF + className;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.contextWrapper = new ApplicationContextWrapper(applicationContext);
    }
}
