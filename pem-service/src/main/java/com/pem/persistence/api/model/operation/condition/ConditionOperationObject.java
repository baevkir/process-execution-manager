package com.pem.persistence.api.model.operation.condition;

import com.pem.persistence.api.model.calculator.common.ConditionCalculatorObject;
import com.pem.persistence.api.model.operation.common.OperationObject;
import com.pem.persistence.api.model.operation.condition.state.AbstractState;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public abstract class ConditionOperationObject<S extends AbstractState> extends OperationObject {

    private List<S> states;

    @DBRef
    private ConditionCalculatorObject calculatorEntity;

    public List<S> getStates() {
        return states;
    }

    public void setStates(List<S> states) {
        this.states = states;
    }

    public ConditionCalculatorObject getCalculatorEntity() {
        return calculatorEntity;
    }

    public void setCalculatorEntity(ConditionCalculatorObject calculatorEntity) {
        this.calculatorEntity = calculatorEntity;
    }

    @Override
    public String toString() {
        return "ConditionOperationObject{" +
                "states=" + states +
                ", calculatorEntity=" + calculatorEntity +
                "} " + super.toString();
    }
}
