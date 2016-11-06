package com.pem.operation.loop.condition;

import com.pem.operation.basic.Operation;
import com.pem.conditioncalculator.BinaryConditionCalculator;

public interface ConditionLoopOperation extends Operation {
    void setCalculator(BinaryConditionCalculator calculator);
    void setOperation(Operation operation);
}
