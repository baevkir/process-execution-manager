package com.pem.core.operation.condition;

import com.pem.core.operation.basic.Operation;
import com.pem.core.conditioncalculator.ConditionCalculator;

public interface ConditionOperation<S, C extends ConditionCalculator<S>> extends Operation {
    void addCondition(S state, Operation operation);
    void setCalculator(C calculator);
}
