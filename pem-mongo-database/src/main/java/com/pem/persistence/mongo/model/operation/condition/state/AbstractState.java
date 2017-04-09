package com.pem.persistence.mongo.model.operation.condition.state;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="type")
public abstract class AbstractState<S> {
    private S state;

    @DBRef
    private OperationEntity operation;

    public S getState() {
        return state;
    }

    public void setState(S state) {
        this.state = state;
    }

    public OperationEntity getOperation() {
        return operation;
    }

    public void setOperation(OperationEntity operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "AbstractState{" +
                "state=" + state +
                ", operation=" + operation +
                '}';
    }
}
