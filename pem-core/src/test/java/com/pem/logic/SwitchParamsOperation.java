package com.pem.logic;

import com.pem.operation.basic.AbstractOperation;
import com.pem.operation.basic.Operation;
import com.pem.context.OperationContext;

import java.math.BigDecimal;

public class SwitchParamsOperation extends AbstractOperation implements Operation {
    @Override
    public void execute(OperationContext context) {
        MathOperationContext mathContext = new MathOperationContext(context);
        BigDecimal firstParam = mathContext.getFirstParam();
        BigDecimal secondParam = mathContext.getSecondParam();

        mathContext.setFirstParam(secondParam);
        mathContext.setSecondParam(firstParam);
    }
}
