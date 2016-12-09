package com.pem.logic.service.executor;

import com.pem.core.context.OperationContext;
import com.pem.model.proccess.ExecutionProcessDTO;

public interface OperationExecutor {
    void execute(ExecutionProcessDTO executionProcess, OperationContext context);
}
