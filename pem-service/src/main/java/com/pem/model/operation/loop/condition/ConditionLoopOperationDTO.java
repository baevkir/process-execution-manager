package com.pem.model.operation.loop.condition;

import com.pem.model.calculator.common.CalculatorDTO;
import com.pem.model.operation.loop.LoopOperationDTO;

public abstract class ConditionLoopOperationDTO extends LoopOperationDTO {

    private CalculatorDTO<Boolean> calculator;

    public CalculatorDTO<Boolean> getCalculator() {
        return calculator;
    }

    public void setCalculator(CalculatorDTO<Boolean> calculator) {
        this.calculator = calculator;
    }

    @Override
    public String toString() {
        return "ConditionLoopOperationDTO{" +
                "calculator=" + calculator +
                "} " + super.toString();
    }
}
