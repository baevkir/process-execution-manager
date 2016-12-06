package com.pem.persistence.api.service.process;

import com.pem.model.proccess.ExecutionProcessDTO;

import java.math.BigInteger;
import java.util.List;

public interface ProcessPersistenceService {
    ExecutionProcessDTO createProcess(ExecutionProcessDTO processEntity);
    void updateProcess(ExecutionProcessDTO processEntity);
    ExecutionProcessDTO getProcess(BigInteger id);
    List<ExecutionProcessDTO> getAllProcesses();
}
