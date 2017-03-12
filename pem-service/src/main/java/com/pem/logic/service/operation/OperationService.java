package com.pem.logic.service.operation;

import com.pem.model.operation.common.OperationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface OperationService {
    Mono<OperationDTO> createOperation(OperationDTO operation);
    Mono<Void> updateOperation(OperationDTO operation);
    Mono<Void> deleteOperation(BigInteger id);
    Mono<OperationDTO> getOperation(BigInteger id);
    Flux<OperationDTO> getAllOperations();
}
