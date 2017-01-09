package com.pem.logic.service.process;

import com.pem.core.context.OperationContextFactory;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.math.BigInteger;

public interface ExecutionProcessService {
    Single<ExecutionProcessDTO> createExecutionProcess(OperationDTO operationEntity);

    Completable updateExecutionProcess(ExecutionProcessDTO processEntity);

    Completable executeProcess(ExecutionProcessDTO executionProcess, OperationContextFactory contextFactory);

    Single<ExecutionProcessDTO> getExecutionProcess(BigInteger id);

    Observable<ExecutionProcessDTO> getAllExecutionProcesses();
}
