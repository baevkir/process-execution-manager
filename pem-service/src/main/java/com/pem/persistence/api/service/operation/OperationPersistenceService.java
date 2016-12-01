package com.pem.persistence.api.service.operation;

import com.pem.model.operation.common.OperationDTO;

import java.math.BigInteger;
import java.util.List;

public interface OperationPersistenceService {
    OperationDTO createOperation(OperationDTO operation);
    void updateOperation(OperationDTO operation);
    OperationDTO getOperation(BigInteger id);
    List<OperationDTO> getAllOperations();
    <O extends OperationDTO> List<O> getOperationsByType(Class<O> targetClass);
    void deleteOperation(BigInteger id);
}
