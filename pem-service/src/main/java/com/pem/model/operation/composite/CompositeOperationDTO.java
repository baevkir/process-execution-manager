package com.pem.model.operation.composite;

import com.pem.model.operation.common.OperationDTO;

import java.util.List;

public abstract class CompositeOperationDTO extends OperationDTO {

    private List<OperationDTO> operationEntities;

    public List<OperationDTO> getOperationEntities() {
        return operationEntities;
    }

    public void setOperationEntities(List<OperationDTO> operationEntities) {
        this.operationEntities = operationEntities;
    }

    @Override
    public String toString() {
        return "CompositeOperationDTO{" +
                super.toString() +
                "operationEntities=" + operationEntities +
                '}';
    }
}
