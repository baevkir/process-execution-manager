package com.pem.logic.service.process.executor;

import com.pem.core.context.OperationContext;
import com.pem.core.context.OperationContextFactory;
import com.pem.model.proccess.ExecutionProcessObject;
import reactor.core.publisher.Mono;

public interface ProcessExecutor {
    Mono<OperationContext> execute(ExecutionProcessObject executionProcess, Mono<OperationContextFactory> contextFactory);
}
