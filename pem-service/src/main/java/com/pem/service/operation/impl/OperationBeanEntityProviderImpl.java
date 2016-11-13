package com.pem.service.operation.impl;

import com.pem.common.provider.operation.OperationProvider;
import com.pem.common.provider.operation.impl.RegisterGlobalOperation;
import com.pem.operation.basic.Operation;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.service.calculator.impl.CalculatorBeanEntityProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OperationBeanEntityProviderImpl implements OperationBeanEntityProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorBeanEntityProviderImpl.class);

    private OperationProvider operationProvider;

    public void setOperationProvider(OperationProvider operationProvider) {
        this.operationProvider = operationProvider;
    }

    @Override
    public List<BeanEntity> provideOperationBeanEntity() {
        LOGGER.debug("Get All OperationBeanEntity .");
        List<BeanEntity> operations = new ArrayList<>();

        Map<String, Operation> beans = operationProvider.getAllGlobalOperations();
        for (Map.Entry<String, Operation> entry : beans.entrySet()) {
            BeanEntity operation = new BeanEntity();
            String beanName = entry.getKey();
            LOGGER.trace("Add bean with name {}", beanName);
            operation.setBeanName(beanName);

            Class clazz = AopProxyUtils.ultimateTargetClass(entry.getValue());
            RegisterGlobalOperation annotation = (RegisterGlobalOperation) clazz.getAnnotation(RegisterGlobalOperation.class);
            String name = annotation.value();
            LOGGER.trace("Presentation name for bean {}", name);
            operation.setName(name);

            operations.add(operation);
        }

        return operations;
    }
}
