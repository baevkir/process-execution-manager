package com.pem.service.executor;

import com.pem.operation.basic.Operation;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.proccess.ExecutionProcessEntity;

public interface OperationExecutor {
    void execute(OperationEntity operationEntity);
    void execute(ExecutionProcessEntity executionProcess);
    void execute(Operation operation);
}
