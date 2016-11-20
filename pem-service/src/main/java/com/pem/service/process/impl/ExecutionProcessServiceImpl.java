package com.pem.service.process.impl;

import com.pem.common.utils.ApplicationContextWrapper;
import com.pem.operation.basic.Operation;
import com.pem.persistence.converter.ConverterFactory;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.service.process.ProcessPersistenceService;
import com.pem.service.process.ExecutionProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.math.BigInteger;
import java.util.List;

public class ExecutionProcessServiceImpl implements ExecutionProcessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionProcessServiceImpl.class);

    @Autowired
    private ApplicationContext context;
    private ProcessPersistenceService persistenceService;
    private ConverterFactory converterFactory;

    @Override
    public ExecutionProcessEntity createExecutionProcess(OperationEntity operationEntity) {
        LOGGER.debug("Create new ExecutionProcess for: {}.", operationEntity);
        ExecutionProcessEntity processEntity = converterFactory.convert(operationEntity, ExecutionProcessEntity.class);
        return persistenceService.createProcess(processEntity);
    }

    @Override
    public ExecutionProcessEntity createExecutionProcess(Operation operation) {
        LOGGER.debug("Create new ExecutionProcess for: {}.", operation);
        ApplicationContextWrapper contextWrapper = new ApplicationContextWrapper(context);
        ExecutionProcessEntity processEntity = new ExecutionProcessEntity();
        processEntity.setName(contextWrapper.getBeanName(operation));
        return persistenceService.createProcess(processEntity);
    }

    @Override
    public void updateExecutionProcess(ExecutionProcessEntity processEntity) {
        LOGGER.debug("Update ExecutionProcess: {}.", processEntity);
        persistenceService.updateProcess(processEntity);
    }

    @Override
    public ExecutionProcessEntity getExecutionProcess(BigInteger id) {
        LOGGER.debug("Get ExecutionProcess: {}.", id);
        return persistenceService.getProcess(id);
    }

    @Override
    public List<ExecutionProcessEntity> getAllExecutionProcesses() {
        LOGGER.debug("Get All ExecutionProcesses.");
        return persistenceService.getAllProcesses();
    }

    public void setPersistenceService(ProcessPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
