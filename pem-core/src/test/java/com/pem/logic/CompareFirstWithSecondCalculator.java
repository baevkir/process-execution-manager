package com.pem.logic;

import com.pem.core.calculator.IntegerCalculator;
import com.pem.core.context.OperationContext;

import java.math.BigInteger;

public class CompareFirstWithSecondCalculator implements IntegerCalculator {

    private BigInteger id;

    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public void setId(BigInteger id) {
        this.id = id;
    }

    @Override
    public Integer calculate(OperationContext context) {
        MathOperationContext mathContext = new MathOperationContext(context);
        return mathContext.getFirstParam().compareTo(mathContext.getSecondParam());
    }
}
