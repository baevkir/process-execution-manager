package com.pem.persistence.mongo.model.operation.loop;

import com.pem.persistence.mongo.model.operation.common.OperationEntity;

public abstract class LoopOperationEntity extends OperationEntity {
    private OperationEntity operation;

    public OperationEntity getOperation() {
        return operation;
    }

    public void setOperation(OperationEntity operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "LoopOperationEntity{" +
                "operation=" + operation +
                "} " + super.toString();
    }
}
