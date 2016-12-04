package com.pem.model.operation.loop.condition;

import com.pem.model.calculator.bean.BinaryBeanCalculatorDTO;
import com.pem.model.operation.loop.LoopOperationDTO;

public abstract class ConditionLoopOperationDTO extends LoopOperationDTO {

    private BinaryBeanCalculatorDTO calculator;

    public BinaryBeanCalculatorDTO getCalculator() {
        return calculator;
    }

    public void setCalculator(BinaryBeanCalculatorDTO calculator) {
        this.calculator = calculator;
    }

    @Override
    public String toString() {
        return "ConditionLoopOperationDTO{" +
                "calculator=" + calculator +
                "} " + super.toString();
    }
}
