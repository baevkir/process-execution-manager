package com.pem.service.process;

import com.pem.persistence.model.proccess.ExecutionProcessEntity;

import java.math.BigInteger;
import java.util.List;

public interface ExecutionProcessService {
    ExecutionProcessEntity createExecutionProcess(ExecutionProcessEntity processEntity);

    void updateExecutionProcess(ExecutionProcessEntity processEntity);

    ExecutionProcessEntity getExecutionProcess(BigInteger id);

    List<ExecutionProcessEntity> getAllExecutionProcesses();
}
