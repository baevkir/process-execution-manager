package com.pem.logic.service.process.impl;

import com.pem.core.common.converter.ConverterFactory;
import com.pem.core.context.OperationContext;
import com.pem.logic.common.context.OperationContextFactory;
import com.pem.logic.service.context.OperationContextService;
import com.pem.logic.service.process.ExecutionProcessService;
import com.pem.logic.service.process.executor.ProcessExecutor;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.persistence.api.manager.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class ExecutionProcessServiceImpl implements ExecutionProcessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionProcessServiceImpl.class);

    private PersistenceManager persistenceManager;
    private ConverterFactory converterFactory;
    private ProcessExecutor operationExecutor;
    private OperationContextService operationContextService;

    @Override
    public Mono<ExecutionProcessObject> createExecutionProcess(OperationObject operationObject) {
        LOGGER.debug("Create new ExecutionProcessObject for: {}.", operationObject);
        return Mono.just(operationObject)
                .map(operation -> converterFactory.convert(operation, OperationObject.class, ExecutionProcessObject.class))
                .flatMap(processObject -> persistenceManager.create(processObject))
                .single();
    }

    @Override
    public Mono<Void> updateExecutionProcess(ExecutionProcessObject processEntity) {
        LOGGER.debug("Update ExecutionProcessObject: {}.", processEntity);
         return persistenceManager.update(processEntity);
    }

    @Override
    public Mono<OperationContext> executeProcess(ExecutionProcessObject executionProcess, OperationContextFactory contextFactory) {
       return operationExecutor.execute(executionProcess, contextFactory);
    }

    @Override
    public Mono<OperationContext> executeProcess(ExecutionProcessObject executionProcess) {
        return operationExecutor.execute(executionProcess, operationContextService.getContextFactory());
    }

    @Override
    public Mono<ExecutionProcessObject> getExecutionProcess(BigInteger id) {
        LOGGER.debug("Get ExecutionProcessObject: {}.", id);
        return persistenceManager.getById(id, ExecutionProcessObject.class);
    }

    @Override
    public Flux<ExecutionProcessObject> getAllExecutionProcesses() {
        LOGGER.debug("Get All ExecutionProcesses.");
        return persistenceManager.getAll(ExecutionProcessObject.class);
    }

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    public void setOperationExecutor(ProcessExecutor operationExecutor) {
        this.operationExecutor = operationExecutor;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    public void setOperationContextService(OperationContextService operationContextService) {
        this.operationContextService = operationContextService;
    }
}
