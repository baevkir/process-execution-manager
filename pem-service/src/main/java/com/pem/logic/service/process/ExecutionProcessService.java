package com.pem.logic.service.process;

import com.pem.operation.basic.Operation;
import com.pem.persistence.api.model.operation.common.OperationObject;
import com.pem.persistence.api.model.proccess.ExecutionProcess;

import java.math.BigInteger;
import java.util.List;

public interface ExecutionProcessService {
    ExecutionProcess createExecutionProcess(OperationObject operationEntity);

    ExecutionProcess createExecutionProcess(Operation operation);

    void updateExecutionProcess(ExecutionProcess processEntity);

    ExecutionProcess getExecutionProcess(BigInteger id);

    List<ExecutionProcess> getAllExecutionProcesses();
}
