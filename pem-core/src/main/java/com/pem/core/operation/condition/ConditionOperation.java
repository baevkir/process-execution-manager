package com.pem.core.operation.condition;

import com.pem.core.operation.basic.Operation;

public interface ConditionOperation<C> extends Operation {
    void addCondition(C condition, Operation operation);
}
