package com.pem.model.operation.condition;

import com.pem.model.calculator.common.ConditionCalculatorDTO;
import com.pem.model.operation.common.OperationDTO;

import java.util.Map;

public abstract class ConditionOperationDTO<S> extends OperationDTO {

    private Map<S, OperationDTO> states;

    private ConditionCalculatorDTO<S> calculator;

    public Map<S, OperationDTO> getStates() {
        return states;
    }

    public void setStates(Map<S, OperationDTO> states) {
        this.states = states;
    }

    public ConditionCalculatorDTO<S> getCalculator() {
        return calculator;
    }

    public void setCalculator(ConditionCalculatorDTO<S> calculator) {
        this.calculator = calculator;
    }

    @Override
    public String toString() {
        return "ConditionOperationDTO{" +
                "states=" + states +
                ", calculator=" + calculator +
                "} " + super.toString();
    }
}
