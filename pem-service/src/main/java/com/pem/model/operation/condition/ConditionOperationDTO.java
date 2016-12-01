package com.pem.model.operation.condition;

import com.pem.model.calculator.common.ConditionCalculatorDTO;
import com.pem.model.operation.common.OperationDTO;

import java.util.Map;

public abstract class ConditionOperationDTO<S> extends OperationDTO {

    private Map<S, OperationDTO> states;

    private ConditionCalculatorDTO<S> calculatorEntity;

    public Map<S, OperationDTO> getStates() {
        return states;
    }

    public void setStates(Map<S, OperationDTO> states) {
        this.states = states;
    }

    public ConditionCalculatorDTO<S> getCalculatorEntity() {
        return calculatorEntity;
    }

    public void setCalculatorEntity(ConditionCalculatorDTO<S> calculatorEntity) {
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
