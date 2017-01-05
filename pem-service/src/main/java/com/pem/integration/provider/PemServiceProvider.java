package com.pem.integration.provider;

import com.pem.logic.service.calculator.CalculatorService;
import com.pem.logic.service.process.executor.ProcessExecutor;
import com.pem.logic.service.operation.OperationService;
import com.pem.logic.service.process.ExecutionProcessService;

public interface PemServiceProvider {
    ExecutionProcessService getExecutionProcessService();
    OperationService getOperationService();
    ProcessExecutor getOperationExecutor();
    CalculatorService getCalculatorService();

}
