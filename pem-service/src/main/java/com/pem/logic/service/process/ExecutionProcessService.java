package com.pem.logic.service.process;

import com.pem.model.operation.common.OperationDTO;
import com.pem.core.operation.basic.Operation;
import com.pem.model.proccess.ExecutionProcessDTO;

import java.math.BigInteger;
import java.util.List;

public interface ExecutionProcessService {
    ExecutionProcessDTO createExecutionProcess(OperationDTO operationEntity);

    ExecutionProcessDTO createExecutionProcess(Operation operation);

    void updateExecutionProcess(ExecutionProcessDTO processEntity);

    ExecutionProcessDTO getExecutionProcess(BigInteger id);

    List<ExecutionProcessDTO> getAllExecutionProcesses();
}
