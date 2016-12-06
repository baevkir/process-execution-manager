package com.pem.logic.service.executor.impl;

import com.pem.logic.bean.provider.context.OperationContextProvider;
import com.pem.core.context.OperationContext;
import com.pem.model.operation.common.OperationDTO;
import com.pem.core.operation.basic.Operation;
import com.pem.core.converter.factory.ConverterFactory;
import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.logic.service.executor.OperationExecutor;
import com.pem.logic.service.process.ExecutionProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationExecutorImpl implements OperationExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationExecutorImpl.class);

    private ExecutionProcessService processService;

    private ConverterFactory converterFactory;

    private OperationContextProvider contextProvider;

    @Override
    public void execute(OperationDTO operationEntity) {
        ExecutionProcessDTO executionProcess = processService.createExecutionProcess(operationEntity);
        execute(executionProcess);
    }

    @Override
    public void execute(Operation operation) {
        ExecutionProcessDTO executionProcess = processService.createExecutionProcess(operation);
        execute(executionProcess);
    }

    @Override
    public void execute(ExecutionProcessDTO executionProcess) {
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
