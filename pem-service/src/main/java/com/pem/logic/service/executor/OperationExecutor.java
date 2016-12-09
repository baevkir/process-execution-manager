package com.pem.logic.service.executor;

import com.pem.core.context.OperationContextFactory;
import com.pem.model.proccess.ExecutionProcessDTO;

public interface OperationExecutor {
    void execute(ExecutionProcessDTO executionProcess, OperationContextFactory contextFactory);
}
