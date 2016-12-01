package com.pem.model.operation.loop;

import com.pem.model.operation.common.OperationDTO;

public abstract class LoopOperationDTO extends OperationDTO {
    private OperationDTO operation;

    public OperationDTO getOperation() {
        return operation;
    }

    public void setOperation(OperationDTO operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "LoopOperationDTO{" +
                "operation=" + operation +
                "} " + super.toString();
    }
}
