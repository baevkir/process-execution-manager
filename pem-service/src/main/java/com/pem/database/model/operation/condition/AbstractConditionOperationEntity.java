package com.pem.database.model.operation.condition;

import com.pem.database.model.calculator.common.CalculatorEntity;
import com.pem.database.model.operation.condition.state.AbstarctState;
import com.pem.database.model.operation.common.OperationEntity;

import java.util.List;

public abstract class AbstractConditionOperationEntity<S extends AbstarctState<?>> extends OperationEntity {

    private List<S> states;
    private CalculatorEntity calculatorEntity;

    public List<S> getStates() {
        return states;
    }

    public void setStates(List<S> states) {
        this.states = states;
    }

    public CalculatorEntity getCalculatorEntity() {
        return calculatorEntity;
    }

    public void setCalculatorEntity(CalculatorEntity calculatorEntity) {
        this.calculatorEntity = calculatorEntity;
    }

    @Override
    public String toString() {
        return "AbstractConditionOperationEntity{" +
                "states=" + states +
                ", calculatorEntity=" + calculatorEntity +
                "} " + super.toString();
    }
}
