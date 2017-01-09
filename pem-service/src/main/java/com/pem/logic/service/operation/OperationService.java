package com.pem.logic.service.operation;

import com.pem.model.operation.common.OperationDTO;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.math.BigInteger;

public interface OperationService {
    Single<OperationDTO> createOperation(OperationDTO operationEntity);
    Completable updateOperation(OperationDTO operationEntity);
    Completable deleteOperation(BigInteger id);
    Single<OperationDTO> getOperation(BigInteger id);
    Observable<OperationDTO> getAllOperations();
}
