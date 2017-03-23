package com.pem.persistence.api.service.operation;

import com.pem.model.operation.common.OperationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface OperationPersistenceService {
    Mono<OperationDTO> createOperation(OperationDTO operation);
    Mono<Void> updateOperation(OperationDTO operation);
    Mono<OperationDTO> getOperation(BigInteger id);
    Flux<OperationDTO> getAllOperations();
    <O extends OperationDTO> Flux<O> getOperationsByType(Class<O> targetClass);
    Mono<Void> deleteOperation(BigInteger id);
}
