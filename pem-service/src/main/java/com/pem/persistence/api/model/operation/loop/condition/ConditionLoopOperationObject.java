package com.pem.persistence.api.model.operation.loop.condition;

import com.pem.persistence.api.model.calculator.bean.BinaryBeanConditionCalculator;
import com.pem.persistence.api.model.operation.loop.LoopOperationObject;

public abstract class ConditionLoopOperationObject extends LoopOperationObject {

    private BinaryBeanConditionCalculator calculator;

    public BinaryBeanConditionCalculator getCalculator() {
        return calculator;
    }

    public void setCalculator(BinaryBeanConditionCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public String toString() {
        return "ConditionLoopOperationObject{" +
                "calculator=" + calculator +
                "} " + super.toString();
    }
}
