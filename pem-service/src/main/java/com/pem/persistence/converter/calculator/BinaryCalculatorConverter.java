package com.pem.persistence.converter.calculator;

import com.pem.common.provider.calculator.ConditionCalculatorProvider;
import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.persistence.converter.common.AbstractConditionCalculatorConverter;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.calculator.BinaryCalculator;
import com.pem.persistence.model.common.bean.BeanEntity;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class BinaryCalculatorConverter extends AbstractConditionCalculatorConverter<BinaryCalculator, BinaryConditionCalculator> {

    private ConditionCalculatorProvider calculatorProvider;

    public void setCalculatorProvider(ConditionCalculatorProvider calculatorProvider) {
        this.calculatorProvider = calculatorProvider;
    }

    @Override
    public BinaryConditionCalculator convert(BinaryCalculator source) {
        BeanEntity bean = source.getBean();
        return calculatorProvider.createCalculator(bean.getBeanName(), BinaryConditionCalculator.class);
    }
}
