package com.pem.persistence.api.provider;

import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import com.pem.persistence.api.service.process.ProcessPersistenceService;

public interface PemPersistenceServiceProvider {
    CalculatorPersistenceService getCalculatorPersistenceService();

    OperationPersistenceService getOperationPersistenceService();

    ExecutionRecordPersistenceService getExecutionRecordPersistenceService();

    ProcessPersistenceService getProcessPersistenceService();
}
