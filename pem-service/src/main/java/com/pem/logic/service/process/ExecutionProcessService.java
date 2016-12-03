package com.pem.logic.service.process;

import com.pem.model.operation.common.OperationDTO;
import com.pem.core.operation.basic.Operation;
import com.pem.model.proccess.ExecutionProcess;

import java.math.BigInteger;
import java.util.List;

public interface ExecutionProcessService {
    ExecutionProcess createExecutionProcess(OperationDTO operationEntity);

    ExecutionProcess createExecutionProcess(Operation operation);

    void updateExecutionProcess(ExecutionProcess processEntity);

    ExecutionProcess getExecutionProcess(BigInteger id);

    List<ExecutionProcess> getAllExecutionProcesses();
}
