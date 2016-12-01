package com.pem.persistence.api.model.operation.composite;

import com.pem.persistence.api.model.operation.common.OperationObject;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public abstract class CompositeOperationObject extends OperationObject {

    @DBRef
    private List<OperationObject> operationEntities;

    public List<OperationObject> getOperationEntities() {
        return operationEntities;
    }

    public void setOperationEntities(List<OperationObject> operationEntities) {
        this.operationEntities = operationEntities;
    }

    @Override
    public String toString() {
        return "CompositeOperationObject{" +
                super.toString() +
                "operationEntities=" + operationEntities +
                '}';
    }
}
