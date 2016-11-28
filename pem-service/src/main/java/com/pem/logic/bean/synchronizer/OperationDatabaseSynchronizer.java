package com.pem.logic.bean.synchronizer;

import com.pem.logic.bean.provider.operation.OperationProvider;
import com.pem.operation.basic.Operation;

import javax.annotation.PostConstruct;
import java.util.Map;

public class OperationDatabaseSynchronizer {

    private OperationProvider operationProvider;

    public void setOperationProvider(OperationProvider operationProvider) {
        this.operationProvider = operationProvider;
    }

    @PostConstruct
    public void synchronizeOperationsWithDatabase() {
        Map<String, Operation> operations = operationProvider.getAllGlobalOperations();
    }
}
