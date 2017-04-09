package com.pem.logic.service.operation;

import com.pem.model.operation.common.OperationObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface OperationService {
    Mono<OperationObject> createOperation(OperationObject operation);
    Mono<Void> updateOperation(OperationObject operation);
    Mono<Void> deleteOperation(BigInteger id);
    Mono<OperationObject> getOperation(BigInteger id);
    Flux<OperationObject> getAllOperations();
}
