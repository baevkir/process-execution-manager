package com.pem.logic.service.process.impl;

import com.pem.logic.common.utils.ApplicationContextWrapper;
import com.pem.operation.basic.Operation;
import com.pem.logic.converter.ConverterFactory;
import com.pem.persistence.api.model.operation.common.OperationObject;
import com.pem.persistence.api.model.proccess.ExecutionProcess;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import com.pem.logic.service.process.ExecutionProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.math.BigInteger;
import java.util.List;

public class ExecutionProcessServiceImpl implements ExecutionProcessService, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionProcessServiceImpl.class);

    private ApplicationContext applicationContext;

    private ProcessPersistenceService persistenceService;
    private ConverterFactory converterFactory;

    @Override
    public ExecutionProcess createExecutionProcess(OperationObject operationEntity) {
        LOGGER.debug("Create new ExecutionProcess for: {}.", operationEntity);
        ExecutionProcess processEntity = converterFactory.convert(operationEntity, ExecutionProcess.class);
        return persistenceService.createProcess(processEntity);
    }

    @Override
    public ExecutionProcess createExecutionProcess(Operation operation) {
        LOGGER.debug("Create new ExecutionProcess for: {}.", operation);
        ApplicationContextWrapper contextWrapper = new ApplicationContextWrapper(applicationContext);
        ExecutionProcess processEntity = new ExecutionProcess();
        processEntity.setName(contextWrapper.getBeanName(operation));
        return persistenceService.createProcess(processEntity);
    }

    @Override
    public void updateExecutionProcess(ExecutionProcess processEntity) {
        LOGGER.debug("Update ExecutionProcess: {}.", processEntity);
        persistenceService.updateProcess(processEntity);
    }

    @Override
    public ExecutionProcess getExecutionProcess(BigInteger id) {
        LOGGER.debug("Get ExecutionProcess: {}.", id);
        return persistenceService.getProcess(id);
    }

    @Override
    public List<ExecutionProcess> getAllExecutionProcesses() {
        LOGGER.debug("Get All ExecutionProcesses.");
        return persistenceService.getAllProcesses();
    }

    public void setPersistenceService(ProcessPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
