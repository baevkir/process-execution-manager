package com.pem.logic.converter.common;

import com.pem.core.converter.impl.Converter;
import com.pem.logic.bean.provider.calculator.ConditionCalculatorProvider;
import com.pem.core.conditioncalculator.ConditionCalculator;

public abstract class AbstractConditionCalculatorConverter<S, T extends ConditionCalculator> implements Converter<S, T> {

    private ConditionCalculatorProvider calculatorProvider;

    public void setCalculatorProvider(ConditionCalculatorProvider calculatorProvider) {
        this.calculatorProvider = calculatorProvider;
    }

    protected ConditionCalculatorProvider getCalculatorProvider() {
        return calculatorProvider;
    }
}
