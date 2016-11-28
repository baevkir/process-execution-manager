package com.pem.common.bean.provider.operation;

import com.pem.operation.basic.Operation;

import java.util.Map;

public interface OperationProvider {
    <O extends Operation> O createCommonOperation(Class<O> operationClass);

    <O extends Operation> O createOperation(String beanName, Class<O> operationClass);

    Map<String, Operation> getAllGlobalOperations();
}
