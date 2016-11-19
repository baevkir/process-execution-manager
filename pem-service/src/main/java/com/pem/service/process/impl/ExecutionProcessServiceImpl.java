package com.pem.service.process.impl;

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
    public ExecutionProcessEntity createExecutionProcess(ExecutionProcessEntity processEntity) {
        LOGGER.debug("Create new ExecutionProcess: {}.", processEntity);
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
