package com.pem.persistence.model.operation.condition.state;

import com.pem.persistence.model.operation.common.OperationEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;

public abstract class AbstarctState<C> {
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
        return "AbstarctState{" +
                "conditionValue=" + conditionValue +
                ", operationEntity=" + operationEntity +
                '}';
    }
}
