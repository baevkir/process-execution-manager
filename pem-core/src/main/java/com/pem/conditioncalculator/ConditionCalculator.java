package com.pem.conditioncalculator;

import com.pem.context.OperationContext;

public interface ConditionCalculator<C> {
    String getConditionCalculatorId();
    void setConditionCalculatorId(String id);
    C calculate(OperationContext context);
}
