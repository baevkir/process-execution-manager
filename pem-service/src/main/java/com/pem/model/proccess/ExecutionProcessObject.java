package com.pem.model.proccess;

import com.pem.model.common.BaseObject;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.proccess.record.ExecutionRecordObject;

import java.util.List;

public class ExecutionProcessObject extends BaseObject {

    private OperationObject executionOperation;

    private List<ExecutionRecordObject> executionRecords;

    public OperationObject getExecutionOperation() {
        return executionOperation;
    }

    public void setExecutionOperation(OperationObject executionOperation) {
        this.executionOperation = executionOperation;
    }

    public List<ExecutionRecordObject> getExecutionRecords() {
        return executionRecords;
    }

    public void setExecutionRecords(List<ExecutionRecordObject> executionRecords) {
        this.executionRecords = executionRecords;
    }

    @Override
    public String toString() {
        return "ExecutionProcessObject{" +
                ", executionOperation=" + executionOperation +
                ", executionRecords=" + executionRecords +
                "} " + super.toString();
    }
}
