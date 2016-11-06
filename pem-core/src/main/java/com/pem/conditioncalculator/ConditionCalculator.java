package com.pem.conditioncalculator;

import com.pem.context.OperationContext;

public interface ConditionCalculator<C> {
    C calculate(OperationContext context);
}
