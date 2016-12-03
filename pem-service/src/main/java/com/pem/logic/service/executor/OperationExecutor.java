package com.pem.logic.service.executor;

import com.pem.core.operation.basic.Operation;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcess;

public interface OperationExecutor {
    void execute(OperationDTO operationEntity);
    void execute(ExecutionProcess executionProcess);
    void execute(Operation operation);
}
