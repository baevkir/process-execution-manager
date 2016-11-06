package com.pem.operation.condition;

import com.pem.operation.basic.Operation;
import com.pem.operation.condition.calculator.ConditionCalculator;

public interface ConditionOperation<S, C extends ConditionCalculator<S>> extends Operation {
    void addCondition(S state, Operation operation);
    void setCalculator(C calculator);
}
