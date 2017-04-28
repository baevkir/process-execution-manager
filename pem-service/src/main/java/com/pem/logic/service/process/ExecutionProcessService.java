package com.pem.logic.service.process;

import com.pem.core.context.OperationContext;
import com.pem.core.context.OperationContextFactory;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.proccess.ExecutionProcessObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface ExecutionProcessService {
    Mono<ExecutionProcessObject> createExecutionProcess(OperationObject operationEntity);

    Mono<Void> updateExecutionProcess(ExecutionProcessObject processEntity);

    Mono<OperationContext> executeProcess(ExecutionProcessObject executionProcess, OperationContextFactory contextFactory);

    Mono<ExecutionProcessObject> getExecutionProcess(BigInteger id);

    Flux<ExecutionProcessObject> getAllExecutionProcesses();
}
