package com.pem.logic.converter.common;

import com.pem.core.common.converter.impl.Converter;
import com.pem.logic.bean.provider.calculator.CalculatorProvider;
import com.pem.core.calculator.Calculator;

public abstract class AbstractConditionCalculatorConverter<S, T extends Calculator> implements Converter<S, T> {

    private CalculatorProvider calculatorProvider;

    public void setCalculatorProvider(CalculatorProvider calculatorProvider) {
        this.calculatorProvider = calculatorProvider;
    }

    protected CalculatorProvider getCalculatorProvider() {
        return calculatorProvider;
    }
}
