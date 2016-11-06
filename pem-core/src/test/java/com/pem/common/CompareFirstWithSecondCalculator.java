package com.pem.common;

import com.pem.context.OperationContext;
import com.pem.operation.condition.AbstractConditionOperation;
import com.pem.operation.condition.calculator.ConditionCalculator;

public class CompareFirstWithSecondCalculator implements ConditionCalculator<Integer> {

    @Override
    public Integer calculate(OperationContext context) {
        MathOperationContext mathContext = new MathOperationContext(context);
        return mathContext.getFirstParam().compareTo(mathContext.getSecondParam());
    }
}
