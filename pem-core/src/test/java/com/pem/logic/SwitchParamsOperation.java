package com.pem.logic;

import com.pem.core.operation.basic.AbstractOperation;
import com.pem.core.operation.basic.Operation;
import com.pem.core.context.OperationContext;

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
