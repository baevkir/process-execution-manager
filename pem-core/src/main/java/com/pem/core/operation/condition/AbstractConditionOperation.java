package com.pem.core.operation.condition;

import com.pem.core.calculator.Calculator;
import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.AbstractOperation;
import com.pem.core.operation.basic.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractConditionOperation<S, C extends Calculator<S>> extends AbstractOperation implements ConditionOperation<S, C> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConditionOperation.class);

    private final Map<S, Operation> conditions = new HashMap<>();

    private Calculator<S> calculator;

    @Override
    public void execute(OperationContext context) {
        S state = calculator.calculate(context);
        Assert.notNull(state, String.format("Can't calculate condition for %s.", getClass()));

        Operation operation = getOperationForState(state);
        operation.execute(context);
    }

    @Override
    public void addCondition(S state, Operation operation) {
        conditions.put(state, operation);
    }

    @Override
    public void setCalculator(C calculator) {
        this.calculator = calculator;
    }

    private Operation getOperationForState(S state) {
        LOGGER.debug("Start to find Operation for condition {}.", state);
        Operation operation = conditions.get(state);
        Assert.notNull(operation, String.format("Can't find Operation for condition %s.", state));
        return operation;
    }

}
