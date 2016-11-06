package com.pem.operation.condition.calculator;

import com.pem.context.OperationContext;

public interface ConditionCalculator<C> {
    C calculate(OperationContext context);
}
