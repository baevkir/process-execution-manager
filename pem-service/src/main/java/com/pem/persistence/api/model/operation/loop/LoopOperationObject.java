package com.pem.persistence.api.model.operation.loop;

import com.pem.persistence.api.model.operation.common.OperationObject;

public abstract class LoopOperationObject extends OperationObject {
    private OperationObject operation;

    public OperationObject getOperation() {
        return operation;
    }

    public void setOperation(OperationObject operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "LoopOperationObject{" +
                "operation=" + operation +
                "} " + super.toString();
    }
}
