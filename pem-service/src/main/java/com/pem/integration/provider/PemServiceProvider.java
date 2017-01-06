package com.pem.integration.provider;

import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.service.calculator.CalculatorService;
import com.pem.logic.service.process.ExecutionProcessService;
import com.pem.logic.service.process.executor.ProcessExecutor;

public interface PemServiceProvider {
    ExecutionProcessService getExecutionProcessService();
    ProcessExecutor getOperationExecutor();
    CalculatorService getCalculatorService();
    ServiceEventBus getServiceEventBus();
}
