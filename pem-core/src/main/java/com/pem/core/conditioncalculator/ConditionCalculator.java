package com.pem.core.conditioncalculator;

import com.pem.core.context.OperationContext;

public interface ConditionCalculator<C> {
    String getConditionCalculatorId();
    void setConditionCalculatorId(String id);
    C calculate(OperationContext context);
}
