package com.pem.logic.service.executor;

import com.pem.operation.basic.Operation;
import com.pem.persistence.api.model.operation.common.OperationObject;
import com.pem.persistence.api.model.proccess.ExecutionProcess;

public interface OperationExecutor {
    void execute(OperationObject operationEntity);
    void execute(ExecutionProcess executionProcess);
    void execute(Operation operation);
}
