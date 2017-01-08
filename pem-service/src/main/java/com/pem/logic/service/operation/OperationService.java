package com.pem.logic.service.operation;

import com.pem.model.operation.common.OperationDTO;
import rx.Observable;

import java.math.BigInteger;

public interface OperationService {
    Observable<OperationDTO> createOperation(OperationDTO operationEntity);
    Observable<Void> updateOperation(OperationDTO operationEntity);
    Observable<Void> deleteOperation(BigInteger id);
    Observable<OperationDTO> getOperation(BigInteger id);
    Observable<OperationDTO> getAllOperations();
}
