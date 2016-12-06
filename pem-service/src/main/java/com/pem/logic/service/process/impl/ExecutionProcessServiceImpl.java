package com.pem.logic.service.process.impl;

import com.pem.core.common.utils.ApplicationContextWrapper;
import com.pem.model.operation.common.OperationDTO;
import com.pem.core.operation.basic.Operation;
import com.pem.core.converter.factory.ConverterFactory;
import com.pem.model.proccess.ExecutionProcessDTO;
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
    public ExecutionProcessDTO createExecutionProcess(OperationDTO operationEntity) {
        LOGGER.debug("Create new ExecutionProcessDTO for: {}.", operationEntity);
        ExecutionProcessDTO processEntity = converterFactory.convert(operationEntity, ExecutionProcessDTO.class);
        return persistenceService.createProcess(processEntity);
    }

    @Override
    public ExecutionProcessDTO createExecutionProcess(Operation operation) {
        LOGGER.debug("Create new ExecutionProcessDTO for: {}.", operation);
        ApplicationContextWrapper contextWrapper = new ApplicationContextWrapper(applicationContext);
        ExecutionProcessDTO processEntity = new ExecutionProcessDTO();
        processEntity.setName(contextWrapper.getBeanName(operation));
        return persistenceService.createProcess(processEntity);
    }

    @Override
    public void updateExecutionProcess(ExecutionProcessDTO processEntity) {
        LOGGER.debug("Update ExecutionProcessDTO: {}.", processEntity);
        persistenceService.updateProcess(processEntity);
    }

    @Override
    public ExecutionProcessDTO getExecutionProcess(BigInteger id) {
        LOGGER.debug("Get ExecutionProcessDTO: {}.", id);
        return persistenceService.getProcess(id);
    }

    @Override
    public List<ExecutionProcessDTO> getAllExecutionProcesses() {
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
