package com.pem.logic.service.operation;

import com.pem.persistence.api.model.operation.common.OperationObject;

import java.math.BigInteger;
import java.util.List;

public interface OperationService {
    OperationObject createOperation(OperationObject operationEntity);
    void updateOperation(OperationObject operationEntity);
    void deleteOperation(BigInteger id);
    OperationObject getOperation(BigInteger id);
    List<OperationObject> getAllOperations();
    void runOperation(BigInteger id);
}
