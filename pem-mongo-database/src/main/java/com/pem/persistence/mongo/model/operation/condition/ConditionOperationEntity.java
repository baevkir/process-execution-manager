package com.pem.persistence.mongo.model.operation.condition;

import com.pem.persistence.mongo.model.calculator.common.CalculatorEntity;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.condition.state.AbstractState;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public abstract class ConditionOperationEntity<E, S extends AbstractState<E>> extends OperationEntity {

    private List<S> states;

    @DBRef
    private CalculatorEntity<E> calculatorEntity;

    public List<S> getStates() {
        return states;
    }

    public void setStates(List<S> states) {
        this.states = states;
    }

    public CalculatorEntity<E> getCalculatorEntity() {
        return calculatorEntity;
    }

    public void setCalculatorEntity(CalculatorEntity<E> calculatorEntity) {
        this.calculatorEntity = calculatorEntity;
    }

    @Override
    public String toString() {
        return "ConditionOperationDTO{" +
                "states=" + states +
                ", calculatorEntity=" + calculatorEntity +
                "} " + super.toString();
    }
}
