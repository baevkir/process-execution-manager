package com.pem.logic.service.process.executor.impl;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.context.OperationContext;
import com.pem.logic.common.context.OperationContextFactory;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.service.process.executor.ProcessExecutor;
import com.pem.model.proccess.ExecutionProcessObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ProcessExecutorImpl implements ProcessExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessExecutorImpl.class);

    private ConverterFactory converterFactory;

    @Override
    public Mono<OperationContext> execute(ExecutionProcessObject executionProcess, OperationContextFactory contextFactory) {
        Mono<OperationContext> context = contextFactory
                .setId(executionProcess.getId())
                .createContext()
                .doOnNext(operationContext -> operationContext.open());

        return Mono.just(executionProcess)
                .map(executionProcessObject -> executionProcess.getExecutionOperation())
                .map(executionOperation -> converterFactory.convert(executionOperation, Operation.class))
                .doOnNext(executionOperation -> LOGGER.trace("Start to execute operation {}.", executionOperation))
                .doOnNext(operation -> LOGGER.debug("Start execute operation in context {}.", executionProcess.getId()))
                .flatMap(executionOperation -> executionOperation.execute(context))
                .single()
                .doOnNext(operationContext -> operationContext.close())
                .doOnNext(operationContext -> LOGGER.debug("Finish execute operation in context {}.", executionProcess.getId()));
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
