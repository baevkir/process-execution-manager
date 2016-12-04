package com.pem.core.operation.condition;

import com.pem.core.calculator.Calculator;
import com.pem.core.operation.basic.Operation;

public interface ConditionOperation<S, C extends Calculator<S>> extends Operation {
    void addCondition(S state, Operation operation);
    void setCalculator(C calculator);
}
