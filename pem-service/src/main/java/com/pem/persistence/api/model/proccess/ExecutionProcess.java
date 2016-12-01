package com.pem.persistence.api.model.proccess;

import com.pem.persistence.api.model.common.BaseObject;
import com.pem.persistence.api.model.operation.common.OperationObject;
import com.pem.persistence.api.model.proccess.record.ExecutionRecord;

import java.util.List;

public class ExecutionProcess extends BaseObject {

    private OperationObject executionOperation;

    private List<ExecutionRecord> executionRecords;

    public OperationObject getExecutionOperation() {
        return executionOperation;
    }

    public void setExecutionOperation(OperationObject executionOperation) {
        this.executionOperation = executionOperation;
    }

    public List<ExecutionRecord> getExecutionRecords() {
        return executionRecords;
    }

    public void setExecutionRecords(List<ExecutionRecord> executionRecords) {
        this.executionRecords = executionRecords;
    }

    @Override
    public String toString() {
        return "ExecutionProcess{" +
                ", executionOperation=" + executionOperation +
                ", executionRecords=" + executionRecords +
                "} " + super.toString();
    }
}
