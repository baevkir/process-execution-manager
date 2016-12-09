package com.pem.logic.service.executor.impl;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.service.executor.OperationExecutor;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationExecutorImpl implements OperationExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationExecutorImpl.class);

    private ConverterFactory converterFactory;

    @Override
    public void execute(ExecutionProcessDTO executionProcess, OperationContext context) {
        OperationDTO executionOperation = executionProcess.getExecutionOperation();
        LOGGER.trace("Start to execute operation {}.", executionOperation);
        Operation operation = converterFactory.convert(executionOperation, Operation.class);

        context.setId(executionOperation.getId());
        operation.execute(context);
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
