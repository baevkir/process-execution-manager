package com.pem.persistence.api.service.operation;

import com.pem.persistence.api.model.operation.common.OperationObject;

import java.math.BigInteger;
import java.util.List;

public interface OperationPersistenceService {
    OperationObject createOperation(OperationObject operation);
    void updateOperation(OperationObject operation);
    OperationObject getOperation(BigInteger id);
    List<OperationObject> getAllOperations();
    <O extends OperationObject> List<O> getOperationsByType(Class<O> targetClass);
    void deleteOperation(BigInteger id);
}
