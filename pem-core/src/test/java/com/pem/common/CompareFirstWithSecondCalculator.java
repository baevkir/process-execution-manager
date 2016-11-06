package com.pem.common;

import com.pem.context.OperationContext;
import com.pem.conditioncalculator.ConditionCalculator;

public class CompareFirstWithSecondCalculator implements ConditionCalculator<Integer> {

    @Override
    public Integer calculate(OperationContext context) {
        MathOperationContext mathContext = new MathOperationContext(context);
        return mathContext.getFirstParam().compareTo(mathContext.getSecondParam());
    }
}
