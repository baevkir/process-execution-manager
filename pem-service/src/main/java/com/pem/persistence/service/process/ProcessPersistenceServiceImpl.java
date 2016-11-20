package com.pem.persistence.service.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.repository.process.ProcessRepository;
import com.pem.persistence.service.common.AbstractPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class ProcessPersistenceServiceImpl extends AbstractPersistenceService<ExecutionProcessEntity, ProcessRepository> implements ProcessPersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPersistenceService.class);

    @Autowired
    private ProcessRepository repository;

    @Override
    protected ProcessRepository getRepository() {
        return repository;
    }

    @Override
    public ExecutionProcessEntity createProcess(ExecutionProcessEntity processEntity) {
        OperationEntity operationEntity = processEntity.getExecutionOperation();
        if (operationEntity != null) {
            processEntity.setExecutionPlan(serializeExecutionOperation(operationEntity));
        }
        return create(processEntity);
    }

    @Override
    public void updateProcess(ExecutionProcessEntity processEntity) {
        OperationEntity operationEntity = processEntity.getExecutionOperation();
        if (operationEntity != null) {
            processEntity.setExecutionPlan(serializeExecutionOperation(operationEntity));
        }
        update(processEntity);
    }

    @Override
    public ExecutionProcessEntity getProcess(BigInteger id) {
        ExecutionProcessEntity executionProcessEntity = getOne(id);
        String json = executionProcessEntity.getExecutionPlan();
        if (json != null) {
            executionProcessEntity.setExecutionOperation(deserializeExecutionOperation(json));
        }
        return executionProcessEntity;
    }

    @Override
    public List<ExecutionProcessEntity> getAllProcesses() {
        return getAll();
    }

    private String serializeExecutionOperation(OperationEntity operation) {
        ObjectMapper mapper = new ObjectMapper();
        try {
           return mapper.writeValueAsString(operation);
        } catch (JsonProcessingException exception) {
           throw new RuntimeException("Can`t serialize Execution Operation " + operation, exception);
        }
    }

    private OperationEntity deserializeExecutionOperation(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, OperationEntity.class);
        } catch (IOException exception) {
            throw new RuntimeException("Can`t deserialize Execution Operation " + json, exception);
        }
    }
}