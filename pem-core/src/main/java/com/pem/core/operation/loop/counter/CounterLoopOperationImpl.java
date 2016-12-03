package com.pem.core.operation.loop.counter;

import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.basic.util.AnnotationOperation;
import com.pem.core.operation.basic.AbstractOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class CounterLoopOperationImpl extends AbstractOperation implements CounterLoopOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationOperation.class);

    private Integer count;
    private Operation operation;

    @Override
    public void execute(OperationContext context) {
        Assert.notNull(count, String.format("Can`t execute %s. Count isn't specified.", getClass()));
        Assert.notNull(operation, String.format("Can`t execute %s. Operation isn't specified.", getClass()));

        for (int i = 0; i < count; i++) {
            operation.execute(context);
        }
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
