package com.pem.model.operation.composite;

import com.pem.model.operation.common.OperationObject;

import java.util.List;

public abstract class CompositeOperationDTO extends OperationObject {

    private List<OperationObject> operations;

    public List<OperationObject> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationObject> operations) {
        this.operations = operations;
    }

    @Override
    public String toString() {
        return "CompositeOperationDTO{" +
                super.toString() +
                "operations=" + operations +
                '}';
    }
}
