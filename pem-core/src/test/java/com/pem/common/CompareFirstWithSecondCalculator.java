package com.pem.common;

import com.pem.conditioncalculator.IntegerConditionCalculator;
import com.pem.context.OperationContext;

public class CompareFirstWithSecondCalculator implements IntegerConditionCalculator {

    @Override
    public Integer calculate(OperationContext context) {
        MathOperationContext mathContext = new MathOperationContext(context);
        return mathContext.getFirstParam().compareTo(mathContext.getSecondParam());
    }
}
