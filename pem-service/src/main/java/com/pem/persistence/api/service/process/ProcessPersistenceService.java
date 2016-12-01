package com.pem.persistence.api.service.process;

import com.pem.persistence.api.model.proccess.ExecutionProcess;

import java.math.BigInteger;
import java.util.List;

public interface ProcessPersistenceService {
    ExecutionProcess createProcess(ExecutionProcess processEntity);
    void updateProcess(ExecutionProcess processEntity);
    ExecutionProcess getProcess(BigInteger id);
    List<ExecutionProcess> getAllProcesses();
}
