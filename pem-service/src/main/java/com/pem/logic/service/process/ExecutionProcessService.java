package com.pem.logic.service.process;

import com.pem.core.context.OperationContextFactory;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;
import rx.Observable;

import java.math.BigInteger;

public interface ExecutionProcessService {
    Observable<ExecutionProcessDTO> createExecutionProcess(OperationDTO operationEntity);

    Observable<ExecutionProcessDTO> updateExecutionProcess(ExecutionProcessDTO processEntity);

    Observable<ExecutionProcessDTO> executeProcess(ExecutionProcessDTO executionProcess, OperationContextFactory contextFactory);

    Observable<ExecutionProcessDTO> getExecutionProcess(BigInteger id);

    Observable<ExecutionProcessDTO> getAllExecutionProcesses();
}
