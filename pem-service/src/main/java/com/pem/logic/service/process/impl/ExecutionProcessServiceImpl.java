package com.pem.logic.service.process.impl;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.context.OperationContextFactory;
import com.pem.logic.service.process.ExecutionProcessService;
import com.pem.logic.service.process.executor.ProcessExecutor;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class ExecutionProcessServiceImpl implements ExecutionProcessService, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionProcessServiceImpl.class);

    private ApplicationContext applicationContext;

    private ProcessPersistenceService persistenceService;
    private ConverterFactory converterFactory;
    private ProcessExecutor operationExecutor;

    @Override
    public Mono<ExecutionProcessDTO> createExecutionProcess(OperationDTO operationEntity) {
        LOGGER.debug("Create new ExecutionProcessDTO for: {}.", operationEntity);
        ExecutionProcessDTO processEntity = converterFactory.convert(operationEntity, OperationDTO.class, ExecutionProcessDTO.class);
        return Mono.fromCallable(() -> persistenceService.createProcess(processEntity));
    }

    @Override
    public Mono<Void> updateExecutionProcess(ExecutionProcessDTO processEntity) {
        LOGGER.debug("Update ExecutionProcessDTO: {}.", processEntity);
         return Mono.fromRunnable(() -> persistenceService.updateProcess(processEntity));
    }

    @Override
    public Mono<Void> executeProcess(ExecutionProcessDTO executionProcess, OperationContextFactory contextFactory) {
       return Mono.fromRunnable(() -> operationExecutor.execute(executionProcess, contextFactory));
    }

    @Override
    public Mono<ExecutionProcessDTO> getExecutionProcess(BigInteger id) {
        LOGGER.debug("Get ExecutionProcessDTO: {}.", id);
        return Mono.fromCallable(() -> persistenceService.getProcess(id));
    }

    @Override
    public Flux<ExecutionProcessDTO> getAllExecutionProcesses() {
        LOGGER.debug("Get All ExecutionProcesses.");
        return Flux.fromIterable(persistenceService.getAllProcesses());
    }

    public void setPersistenceService(ProcessPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public void setOperationExecutor(ProcessExecutor operationExecutor) {
        this.operationExecutor = operationExecutor;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
