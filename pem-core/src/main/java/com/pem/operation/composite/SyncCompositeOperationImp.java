package com.pem.operation.composite;

import com.pem.operation.basic.Operation;
import com.pem.context.OperationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SyncCompositeOperationImp extends AbstractCompositeOperation implements SyncCompositeOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncCompositeOperationImp.class);

    @Override
    public void execute(OperationContext context) {
        LOGGER.debug("Start {}.", getClass());
        List<Operation> operations = getOperations();
        for (Operation operation: operations) {
            LOGGER.debug("Start to execute {}", operation.getClass());
            operation.execute(context);
            LOGGER.debug("Finish execution {}", operation.getClass());
        }
        LOGGER.debug("Finish {}.", getClass());
    }
}
