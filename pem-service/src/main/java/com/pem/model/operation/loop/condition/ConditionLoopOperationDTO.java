package com.pem.model.operation.loop.condition;

import com.pem.model.calculator.bean.BinaryBeanConditionCalculatorDTO;
import com.pem.model.operation.loop.LoopOperationDTO;

public abstract class ConditionLoopOperationDTO extends LoopOperationDTO {

    private BinaryBeanConditionCalculatorDTO calculator;

    public BinaryBeanConditionCalculatorDTO getCalculator() {
        return calculator;
    }

    public void setCalculator(BinaryBeanConditionCalculatorDTO calculator) {
        this.calculator = calculator;
    }

    @Override
    public String toString() {
        return "ConditionLoopOperationDTO{" +
                "calculator=" + calculator +
                "} " + super.toString();
    }
}
