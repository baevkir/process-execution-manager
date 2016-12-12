package com.pem.logic.bean.provider.operation;

import com.pem.core.operation.basic.Operation;
import com.pem.core.common.bean.BeanObject;

import java.util.Set;

public interface OperationProvider {
    <O extends Operation> O createCommonOperation(Class<O> operationClass);

    <O extends Operation> O createOperation(String beanName, Class<O> operationClass);

    Set<BeanObject> getAllOperationBeanObjects();
}
