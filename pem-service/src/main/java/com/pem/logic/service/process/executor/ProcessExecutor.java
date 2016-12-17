package com.pem.logic.service.process.executor;

import com.pem.core.context.OperationContextFactory;
import com.pem.model.proccess.ExecutionProcessDTO;

public interface ProcessExecutor {
    void execute(ExecutionProcessDTO executionProcess, OperationContextFactory contextFactory);
}
