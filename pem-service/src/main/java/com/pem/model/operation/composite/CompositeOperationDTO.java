package com.pem.model.operation.composite;

import com.pem.model.operation.common.OperationDTO;

import java.util.List;

public abstract class CompositeOperationDTO extends OperationDTO {

    private List<OperationDTO> operations;

    public List<OperationDTO> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationDTO> operations) {
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
