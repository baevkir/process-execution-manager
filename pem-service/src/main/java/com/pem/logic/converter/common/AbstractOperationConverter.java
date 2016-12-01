package com.pem.logic.converter.common;

import com.pem.logic.bean.provider.operation.OperationProvider;
import com.pem.operation.basic.Operation;

public abstract class AbstractOperationConverter<S> implements Converter<S, Operation> {

    private OperationProvider operationProvider;

    public void setOperationProvider(OperationProvider operationProvider) {
        this.operationProvider = operationProvider;
    }

    protected OperationProvider getOperationProvider() {
        return operationProvider;
    }
}