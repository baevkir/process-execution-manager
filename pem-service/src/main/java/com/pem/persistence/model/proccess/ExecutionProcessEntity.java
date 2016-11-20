package com.pem.persistence.model.proccess;

import com.pem.persistence.model.common.BaseEntity;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.proccess.record.ExecutionRecordEntity;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "execution-processes")
public class ExecutionProcessEntity extends BaseEntity {

    private String executionPlan;

    @Transient
    private OperationEntity executionOperation;

    private List<ExecutionRecordEntity> executionRecords;

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

    public List<ExecutionRecordEntity> getExecutionRecords() {
        return executionRecords;
    }

    public void setExecutionRecords(List<ExecutionRecordEntity> executionRecords) {
        this.executionRecords = executionRecords;
    }

    @Override
    public String toString() {
        return "ExecutionProcessEntity{" +
                "executionPlan='" + executionPlan + '\'' +
                ", executionOperation=" + executionOperation +
                ", executionRecords=" + executionRecords +
                "} " + super.toString();
    }
}
