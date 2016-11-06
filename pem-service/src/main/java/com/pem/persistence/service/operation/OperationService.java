package com.pem.persistence.service.operation;

import com.pem.persistence.model.operation.common.OperationEntity;

import java.math.BigInteger;
import java.util.List;

public interface OperationService {
    OperationEntity createOperation(OperationEntity operationEntity);
    void updateOperation(OperationEntity operationEntity);
    OperationEntity getOperation(BigInteger id);
    List<OperationEntity> getAllOperations();
    void deleteOperation(BigInteger id);
}
