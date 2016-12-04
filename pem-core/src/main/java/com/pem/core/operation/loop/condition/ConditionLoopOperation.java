package com.pem.core.operation.loop.condition;

import com.pem.core.operation.basic.Operation;
import com.pem.core.calculator.BinaryCalculator;

public interface ConditionLoopOperation extends Operation {
    void setCalculator(BinaryCalculator calculator);
    void setOperation(Operation operation);
}
