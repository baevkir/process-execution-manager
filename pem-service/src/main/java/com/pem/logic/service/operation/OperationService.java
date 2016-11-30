package com.pem.logic.service.operation;

import com.pem.persistence.model.operation.common.OperationEntity;

import java.math.BigInteger;
import java.util.List;

public interface OperationService {
    OperationEntity createOperation(OperationEntity operationEntity);
    void updateOperation(OperationEntity operationEntity);
    void deleteOperation(BigInteger id);
    OperationEntity getOperation(BigInteger id);
    List<OperationEntity> getAllOperations();
    void runOperation(BigInteger id);
}
