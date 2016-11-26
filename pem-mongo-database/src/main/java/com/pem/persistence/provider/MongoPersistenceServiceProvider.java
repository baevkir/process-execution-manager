package com.pem.persistence.provider;

import com.pem.persistence.api.provider.PersistenceServiceProvider;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import com.pem.persistence.api.service.process.ProcessPersistenceService;

public class MongoPersistenceServiceProvider implements PersistenceServiceProvider {

    @Override
    public CalculatorPersistenceService getCalculatorPersistenceService() {
        return null;
    }

    @Override
    public OperationPersistenceService getOperationPersistenceService() {
        return null;
    }

    @Override
    public ExecutionRecordPersistenceService getExecutionRecordPersistenceService() {
        return null;
    }

    @Override
    public ProcessPersistenceService getProcessPersistenceService() {
        return null;
    }
}
