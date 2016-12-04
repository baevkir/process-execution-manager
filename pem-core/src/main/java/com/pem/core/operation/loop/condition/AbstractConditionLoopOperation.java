package com.pem.core.operation.loop.condition;

import com.pem.core.operation.basic.AbstractOperation;
import com.pem.core.operation.basic.util.AnnotationOperation;
import com.pem.core.operation.basic.Operation;
import com.pem.core.calculator.BinaryCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConditionLoopOperation extends AbstractOperation implements ConditionLoopOperation{

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationOperation.class);
    private Operation operation;
    private BinaryCalculator calculator;

    @Override
    public void setCalculator(BinaryCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    protected Operation getOperation() {
        return operation;
    }

    protected BinaryCalculator getCalculator() {
        return calculator;
    }
}
