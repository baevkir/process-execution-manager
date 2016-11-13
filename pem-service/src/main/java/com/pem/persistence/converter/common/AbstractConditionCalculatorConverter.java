package com.pem.persistence.converter.common;

import com.pem.common.provider.calculator.ConditionCalculatorProvider;
import com.pem.conditioncalculator.ConditionCalculator;

public abstract class AbstractConditionCalculatorConverter<S, T extends ConditionCalculator> extends AbstractConverter<S, T> {

    private ConditionCalculatorProvider calculatorProvider;

    public void setCalculatorProvider(ConditionCalculatorProvider calculatorProvider) {
        this.calculatorProvider = calculatorProvider;
    }

    protected ConditionCalculatorProvider getCalculatorProvider() {
        return calculatorProvider;
    }
}
