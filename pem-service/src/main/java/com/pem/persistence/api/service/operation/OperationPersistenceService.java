package com.pem.persistence.api.service.operation;

import com.pem.persistence.model.operation.common.OperationEntity;

import java.math.BigInteger;
import java.util.List;

public interface OperationPersistenceService {
    OperationEntity createOperation(OperationEntity operationEntity);
    void updateOperation(OperationEntity operationEntity);
    OperationEntity getOperation(BigInteger id);
    List<OperationEntity> getAllOperations();
    <O extends OperationEntity> List<O> getOperationsByType(Class<O> targetClass);
    void deleteOperation(BigInteger id);
}
