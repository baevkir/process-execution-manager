package com.pem.logic.service.process;

import com.pem.operation.basic.Operation;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.proccess.ExecutionProcessEntity;

import java.math.BigInteger;
import java.util.List;

public interface ExecutionProcessService {
    ExecutionProcessEntity createExecutionProcess(OperationEntity operationEntity);

    ExecutionProcessEntity createExecutionProcess(Operation operation);

    void updateExecutionProcess(ExecutionProcessEntity processEntity);

    ExecutionProcessEntity getExecutionProcess(BigInteger id);

    List<ExecutionProcessEntity> getAllExecutionProcesses();
}
