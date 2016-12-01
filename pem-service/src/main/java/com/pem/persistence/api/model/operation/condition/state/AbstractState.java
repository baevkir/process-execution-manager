package com.pem.persistence.api.model.operation.condition.state;

import com.pem.persistence.api.model.operation.common.OperationObject;

public abstract class AbstractState<C> {
    private C conditionValue;

    private OperationObject operationEntity;

    public C getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(C conditionValue) {
        this.conditionValue = conditionValue;
    }

    public OperationObject getOperationEntity() {
        return operationEntity;
    }

    public void setOperationEntity(OperationObject operationEntity) {
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
