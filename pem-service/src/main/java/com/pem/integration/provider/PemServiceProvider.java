package com.pem.integration.provider;

import com.pem.logic.service.trigger.TriggerService;
import com.pem.logic.service.operation.OperationService;
import com.pem.logic.service.process.ExecutionProcessService;

public interface PemServiceProvider {
    TriggerService getTriggerService();
    OperationService getOperationService();
    ExecutionProcessService getExecutionProcessService();
}
