package com.pem.persistence.mongo.service.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import com.pem.persistence.mongo.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.mongo.repository.process.ProcessRepository;
import com.pem.persistence.mongo.service.common.AbstractMongoPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class MongoProcessPersistenceService extends AbstractMongoPersistenceService<ExecutionProcessDTO, ExecutionProcessEntity> implements ProcessPersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMongoPersistenceService.class);

    @Autowired
    private ProcessRepository repository;

    private ExecutionRecordPersistenceService executionRecordPersistenceService;

    @Override
    protected ProcessRepository getRepository() {
        return repository;
    }

    @Override
    public ExecutionProcessDTO createProcess(ExecutionProcessDTO processEntity) {
        return create(processEntity);
    }

    @Override
    public void updateProcess(ExecutionProcessDTO processEntity) {
        update(processEntity);
    }

    @Override
    public ExecutionProcessDTO getProcess(BigInteger id) {
        return getOne(id);
    }

    @Override
    public List<ExecutionProcessDTO> getAllProcesses() {
        return getAll();
    }

    private String serializeExecutionOperation(OperationDTO operation) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(operation);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Can`t serialize Execution Operation " + operation, exception);
        }
    }

    private OperationDTO deserializeExecutionOperation(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, OperationDTO.class);
        } catch (IOException exception) {
            throw new RuntimeException("Can`t deserialize Execution Operation " + json, exception);
        }
    }

    public void setExecutionRecordPersistenceService(ExecutionRecordPersistenceService executionRecordPersistenceService) {
        this.executionRecordPersistenceService = executionRecordPersistenceService;
    }
}
