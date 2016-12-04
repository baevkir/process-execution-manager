package com.pem.model.operation.condition;

import com.pem.model.calculator.common.CalculatorDTO;
import com.pem.model.operation.common.OperationDTO;

import java.util.Map;

public abstract class ConditionOperationDTO<S> extends OperationDTO {

    private Map<S, OperationDTO> states;

    private CalculatorDTO<S> calculator;

    public Map<S, OperationDTO> getStates() {
        return states;
    }

    public void setStates(Map<S, OperationDTO> states) {
        this.states = states;
    }

    public CalculatorDTO<S> getCalculator() {
        return calculator;
    }

    public void setCalculator(CalculatorDTO<S> calculator) {
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
