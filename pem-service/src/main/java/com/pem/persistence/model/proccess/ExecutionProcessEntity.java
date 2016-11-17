package com.pem.persistence.model.proccess;

import com.pem.persistence.model.common.BaseEntity;
import com.pem.persistence.model.operation.common.OperationEntity;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "execution-processes")
public class ExecutionProcessEntity extends BaseEntity {

    private String executionPlan;

    @Transient
    private OperationEntity executionOperation;

    public String getExecutionPlan() {
        return executionPlan;
    }

    public void setExecutionPlan(String executionPlan) {
        this.executionPlan = executionPlan;
    }

    public OperationEntity getExecutionOperation() {
        return executionOperation;
    }

    public void setExecutionOperation(OperationEntity executionOperation) {
        this.executionOperation = executionOperation;
    }

    @Override
    public String toString() {
        return "ExecutionProcessEntity{" +
                "executionOperation=" + executionOperation +
                "} " + super.toString();
    }
}
