package com.pem.model.operation.condition;

import com.pem.model.trigger.common.TriggerObject;

public class TriggerOperationObject extends ConditionOperationObject<Integer> {

    private TriggerObject trigger;

    public TriggerObject getTrigger() {
        return trigger;
    }

    public void setTrigger(TriggerObject trigger) {
        this.trigger = trigger;
    }

    @Override
    public String toString() {
        return "TriggerOperationObject{" +
                "trigger=" + trigger +
                "} " + super.toString();
    }
}
