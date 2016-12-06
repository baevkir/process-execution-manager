package com.pem.model.proccess;

import com.pem.model.common.BaseDTO;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.record.ExecutionRecordDTO;

import java.util.List;

public class ExecutionProcessDTO extends BaseDTO {

    private OperationDTO executionOperation;

    private List<ExecutionRecordDTO> executionRecords;

    public OperationDTO getExecutionOperation() {
        return executionOperation;
    }

    public void setExecutionOperation(OperationDTO executionOperation) {
        this.executionOperation = executionOperation;
    }

    public List<ExecutionRecordDTO> getExecutionRecords() {
        return executionRecords;
    }

    public void setExecutionRecords(List<ExecutionRecordDTO> executionRecords) {
        this.executionRecords = executionRecords;
    }

    @Override
    public String toString() {
        return "ExecutionProcessDTO{" +
                ", executionOperation=" + executionOperation +
                ", executionRecords=" + executionRecords +
                "} " + super.toString();
    }
}
