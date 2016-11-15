package com.pem.persistence.service.process;

import com.pem.persistence.model.proccess.ExecutionProcessEntity;

import java.math.BigInteger;
import java.util.List;

public interface ProcessPersistenceService {
    ExecutionProcessEntity createProcess(ExecutionProcessEntity processEntity);
    void updateProcess(ExecutionProcessEntity processEntity);
    ExecutionProcessEntity getProcess(BigInteger id);
    List<ExecutionProcessEntity> getAllProcesses();
}
