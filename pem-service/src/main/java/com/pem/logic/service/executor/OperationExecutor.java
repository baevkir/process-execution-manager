package com.pem.logic.service.executor;

import com.pem.core.operation.basic.Operation;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;

public interface OperationExecutor {
    void execute(OperationDTO operationEntity);
    void execute(ExecutionProcessDTO executionProcess);
    void execute(Operation operation);
}
