package com.pem.model.operation.condition;

import com.pem.model.operation.common.OperationObject;

import java.util.Map;

public abstract class ConditionOperationObject<S> extends OperationObject {

    private Map<S, OperationObject> states;

    public Map<S, OperationObject> getStates() {
        return states;
    }

    public void setStates(Map<S, OperationObject> states) {
        this.states = states;
    }

    @Override
    public String toString() {
        return "ConditionOperationObject{" +
                "states=" + states +
                "} " + super.toString();
    }
}
