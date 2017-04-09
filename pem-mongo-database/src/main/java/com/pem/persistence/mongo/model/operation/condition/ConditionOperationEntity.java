package com.pem.persistence.mongo.model.operation.condition;

import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.condition.state.AbstractState;

import java.util.List;

public abstract class ConditionOperationEntity<S extends AbstractState<?>> extends OperationEntity {

    private List<S> states;

    public List<S> getStates() {
        return states;
    }

    public void setStates(List<S> states) {
        this.states = states;
    }

    @Override
    public String toString() {
        return "ConditionOperationObject{" +
                "states=" + states +
                "} " + super.toString();
    }
}
