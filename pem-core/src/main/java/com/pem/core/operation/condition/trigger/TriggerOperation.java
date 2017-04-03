package com.pem.core.operation.condition.trigger;

import com.pem.core.operation.condition.ConditionOperation;
import com.pem.core.trigger.Trigger;

public interface TriggerOperation extends ConditionOperation<Integer> {
    void setTrigger(Trigger trigger);
}
