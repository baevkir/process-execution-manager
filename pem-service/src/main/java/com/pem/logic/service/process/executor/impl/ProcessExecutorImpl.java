package com.pem.logic.service.process.executor.impl;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.context.OperationContextFactory;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.service.process.executor.ProcessExecutor;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessExecutorImpl implements ProcessExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessExecutorImpl.class);

    private ConverterFactory converterFactory;

    @Override
    public void execute(ExecutionProcessDTO executionProcess, OperationContextFactory contextFactory) {
        OperationDTO executionOperation = executionProcess.getExecutionOperation();
        LOGGER.trace("Start to execute operation {}.", executionOperation);
        Operation operation = converterFactory.convert(executionOperation, Operation.class);

        contextFactory.setId(executionProcess.getId());
        LOGGER.debug("Start execute operation in context {}.", executionProcess.getId());
        operation.execute(contextFactory.createContext());
        LOGGER.debug("Finish execute operation in context {}.", executionProcess.getId());
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
