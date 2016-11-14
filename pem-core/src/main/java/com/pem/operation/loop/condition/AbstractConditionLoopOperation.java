package com.pem.operation.loop.condition;

import com.pem.operation.basic.AbstractOperation;
import com.pem.operation.basic.util.AnnotationOperation;
import com.pem.operation.basic.Operation;
import com.pem.conditioncalculator.BinaryConditionCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConditionLoopOperation extends AbstractOperation implements ConditionLoopOperation{

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationOperation.class);
    private Operation operation;
    private BinaryConditionCalculator calculator;

    @Override
    public void setCalculator(BinaryConditionCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    protected Operation getOperation() {
        return operation;
    }

    protected BinaryConditionCalculator getCalculator() {
        return calculator;
    }
}
