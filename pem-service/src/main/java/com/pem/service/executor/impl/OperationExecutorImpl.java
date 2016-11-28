package com.pem.service.executor.impl;

import com.pem.common.provider.context.OperationContextProvider;
import com.pem.context.OperationContext;
import com.pem.operation.basic.Operation;
import com.pem.common.converter.ConverterFactory;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.proccess.ExecutionProcessEntity;
import com.pem.service.executor.OperationExecutor;
import com.pem.service.process.ExecutionProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationExecutorImpl implements OperationExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationExecutorImpl.class);

    private ExecutionProcessService processService;

    private ConverterFactory converterFactory;

    private OperationContextProvider contextProvider;

    @Override
    public void execute(OperationEntity operationEntity) {
        ExecutionProcessEntity executionProcess = processService.createExecutionProcess(operationEntity);
        execute(executionProcess);
    }

    @Override
    public void execute(Operation operation) {
        ExecutionProcessEntity executionProcess = processService.createExecutionProcess(operation);
        execute(executionProcess);
    }

    @Override
    public void execute(ExecutionProcessEntity executionProcess) {
        OperationContext context = contextProvider.createContext();
        Operation operation = converterFactory.convert(executionProcess.getExecutionOperation(), Operation.class);
        operation.execute(context);
    }

    public void setProcessService(ExecutionProcessService processService) {
        this.processService = processService;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    public void setContextProvider(OperationContextProvider contextProvider) {
        this.contextProvider = contextProvider;
    }
}
