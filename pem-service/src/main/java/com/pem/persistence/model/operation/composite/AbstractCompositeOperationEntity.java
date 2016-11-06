package com.pem.persistence.model.operation.composite;

import com.pem.persistence.model.operation.common.OperationEntity;

import java.util.List;

public abstract class AbstractCompositeOperationEntity extends OperationEntity {

    private List<OperationEntity> operationEntities;

    public List<OperationEntity> getOperationEntities() {
        return operationEntities;
    }

    public void setOperationEntities(List<OperationEntity> operationEntities) {
        this.operationEntities = operationEntities;
    }

    @Override
    public String toString() {
        return "AbstractCompositeOperationEntity{" +
                super.toString() +
                "operationEntities=" + operationEntities +
                '}';
    }
}
