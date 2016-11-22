package com.pem.integration.launcher;

import com.pem.service.calculator.ConditionCalculatorService;
import com.pem.service.executor.OperationExecutor;
import com.pem.service.operation.OperationService;
import com.pem.service.process.ExecutionProcessService;

public interface ProcessExecutionManagerLauncher {
    ExecutionProcessService getExecutionProcessService();
    OperationService getOperationService();
    OperationExecutor getOperationExecutor();
    ConditionCalculatorService getConditionCalculatorService();
}
