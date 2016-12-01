package com.pem.model.proccess;

import com.pem.model.common.BaseDTO;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.record.ExecutionRecord;

import java.util.List;

public class ExecutionProcess extends BaseDTO {

    private OperationDTO executionOperation;

    private List<ExecutionRecord> executionRecords;

    public OperationDTO getExecutionOperation() {
        return executionOperation;
    }

    public void setExecutionOperation(OperationDTO executionOperation) {
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
