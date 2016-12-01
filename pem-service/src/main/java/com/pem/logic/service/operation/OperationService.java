package com.pem.logic.service.operation;

import com.pem.model.operation.common.OperationDTO;

import java.math.BigInteger;
import java.util.List;

public interface OperationService {
    OperationDTO createOperation(OperationDTO operationEntity);
    void updateOperation(OperationDTO operationEntity);
    void deleteOperation(BigInteger id);
    OperationDTO getOperation(BigInteger id);
    List<OperationDTO> getAllOperations();
    void runOperation(BigInteger id);
}
