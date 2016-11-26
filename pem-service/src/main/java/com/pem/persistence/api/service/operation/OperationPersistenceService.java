package com.pem.persistence.api.service.operation;

import com.pem.persistence.model.operation.common.OperationEntity;

import java.math.BigInteger;
import java.util.List;

public interface OperationPersistenceService {
    OperationEntity createOperation(OperationEntity operationEntity);
    void updateOperation(OperationEntity operationEntity);
    OperationEntity getOperation(BigInteger id);
    List<OperationEntity> getAllOperations();
    void deleteOperation(BigInteger id);
}
