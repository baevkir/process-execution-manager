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
import rx.Observable;

import java.math.BigInteger;

public class ExecutionProcessServiceImpl implements ExecutionProcessService, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionProcessServiceImpl.class);

    private ApplicationContext applicationContext;

    private ProcessPersistenceService persistenceService;
    private ConverterFactory converterFactory;
    private ProcessExecutor operationExecutor;

    @Override
    public Observable<ExecutionProcessDTO> createExecutionProcess(OperationDTO operationEntity) {
        LOGGER.debug("Create new ExecutionProcessDTO for: {}.", operationEntity);
        ExecutionProcessDTO processEntity = converterFactory.convert(operationEntity, OperationDTO.class, ExecutionProcessDTO.class);
        return Observable.just(persistenceService.createProcess(processEntity));
    }

    @Override
    public Observable<Void> updateExecutionProcess(ExecutionProcessDTO processEntity) {
        LOGGER.debug("Update ExecutionProcessDTO: {}.", processEntity);
        persistenceService.updateProcess(processEntity);
        return Observable.empty();
    }

    @Override
    public Observable<Void> executeProcess(ExecutionProcessDTO executionProcess, OperationContextFactory contextFactory) {
        operationExecutor.execute(executionProcess, contextFactory);
        return Observable.empty();
    }

    @Override
    public Observable<ExecutionProcessDTO> getExecutionProcess(BigInteger id) {
        LOGGER.debug("Get ExecutionProcessDTO: {}.", id);
        return Observable.just(persistenceService.getProcess(id));
    }

    @Override
    public Observable<ExecutionProcessDTO> getAllExecutionProcesses() {
        LOGGER.debug("Get All ExecutionProcesses.");
        return Observable.from(persistenceService.getAllProcesses());
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
