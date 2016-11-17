package com.pem.persistence.model.operation.condition.state;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pem.persistence.model.operation.common.OperationEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="type")
public abstract class AbstractState<C> {
    private C conditionValue;

    @DBRef
    private OperationEntity operationEntity;

    public C getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(C conditionValue) {
        this.conditionValue = conditionValue;
    }

    public OperationEntity getOperationEntity() {
        return operationEntity;
    }

    public void setOperationEntity(OperationEntity operationEntity) {
        this.operationEntity = operationEntity;
    }

    @Override
    public String toString() {
        return "AbstractState{" +
                "conditionValue=" + conditionValue +
                ", operationEntity=" + operationEntity +
                '}';
    }
}
