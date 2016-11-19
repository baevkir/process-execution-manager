package com.pem.service.process.impl;

import com.pem.operation.basic.Operation;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.service.process.ProcessPersistenceService;
import com.pem.service.process.ExecutionProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;

public class ExecutionProcessServiceImpl implements ExecutionProcessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionProcessServiceImpl.class);

    private ProcessPersistenceService persistenceService;

    @Override
    public ExecutionProcessEntity createExecutionProcess(OperationEntity operationEntity) {
        LOGGER.debug("Create new ExecutionProcess for: {}.", operationEntity);
        ExecutionProcessEntity processEntity = new ExecutionProcessEntity();
        processEntity.setName(operationEntity.getName());


        return persistenceService.createProcess(processEntity);
    }

    @Override
    public ExecutionProcessEntity createExecutionProcess(Operation operation) {
        LOGGER.debug("Create new ExecutionProcess for: {}.", operation);
        ExecutionProcessEntity processEntity = new ExecutionProcessEntity();

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
}
