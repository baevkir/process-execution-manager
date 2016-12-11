package com.pem.integration.launcher;

import com.pem.logic.service.calculator.CalculatorService;
import com.pem.logic.service.executor.OperationExecutor;
import com.pem.logic.service.operation.OperationService;
import com.pem.logic.service.process.ExecutionProcessService;

public interface ProcessExecutionManagerLauncher {
    ExecutionProcessService getExecutionProcessService();
    OperationService getOperationService();
    OperationExecutor getOperationExecutor();
    CalculatorService getConditionCalculatorService();
}
