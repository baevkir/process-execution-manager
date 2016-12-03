package com.pem.logic;

import com.pem.core.conditioncalculator.IntegerConditionCalculator;
import com.pem.core.context.OperationContext;

public class CompareFirstWithSecondCalculator implements IntegerConditionCalculator {

    private String id;

    @Override
    public String getConditionCalculatorId() {
        return id;
    }

    @Override
    public void setConditionCalculatorId(String id) {
        this.id = id;
    }

    @Override
    public Integer calculate(OperationContext context) {
        MathOperationContext mathContext = new MathOperationContext(context);
        return mathContext.getFirstParam().compareTo(mathContext.getSecondParam());
    }
}
