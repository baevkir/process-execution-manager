package com.pem.persistence.mongo.model.operation.composite;

import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public abstract class CompositeOperationEntity extends OperationEntity {

    @DBRef
    private List<OperationEntity> operationEntities;

    public List<OperationEntity> getOperationEntities() {
        return operationEntities;
    }

    public void setOperationEntities(List<OperationEntity> operationEntities) {
        this.operationEntities = operationEntities;
    }

    @Override
    public String toString() {
        return "CompositeOperationEntity{" +
                super.toString() +
                "operationEntities=" + operationEntities +
                '}';
    }
}
