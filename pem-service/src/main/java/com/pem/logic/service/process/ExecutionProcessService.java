package com.pem.logic.service.process;

import com.pem.core.context.OperationContextFactory;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface ExecutionProcessService {
    Mono<ExecutionProcessDTO> createExecutionProcess(OperationDTO operationEntity);

    Mono<Void> updateExecutionProcess(ExecutionProcessDTO processEntity);

    Mono<Void> executeProcess(ExecutionProcessDTO executionProcess, OperationContextFactory contextFactory);

    Mono<ExecutionProcessDTO> getExecutionProcess(BigInteger id);

    Flux<ExecutionProcessDTO> getAllExecutionProcesses();
}
