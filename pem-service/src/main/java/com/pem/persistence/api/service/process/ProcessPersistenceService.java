package com.pem.persistence.api.service.process;

import com.pem.model.proccess.ExecutionProcessDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface ProcessPersistenceService {
    Mono<ExecutionProcessDTO> createProcess(ExecutionProcessDTO processEntity);
    Mono<Void> updateProcess(ExecutionProcessDTO processEntity);
    Mono<ExecutionProcessDTO> getProcess(BigInteger id);
    Flux<ExecutionProcessDTO> getAllProcesses();
}
