package com.pem.core.operation.loop.condition;

import com.pem.core.operation.basic.Operation;
import com.pem.core.conditioncalculator.BinaryConditionCalculator;

public interface ConditionLoopOperation extends Operation {
    void setCalculator(BinaryConditionCalculator calculator);
    void setOperation(Operation operation);
}
