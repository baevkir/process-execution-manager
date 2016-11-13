package com.pem.persistence.converter.calculator;

import com.pem.conditioncalculator.IntegerConditionCalculator;
import com.pem.persistence.converter.common.AbstractConditionCalculatorConverter;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.calculator.IntegerCalculator;
import com.pem.persistence.model.common.bean.BeanEntity;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class IntegerCalculatorConverter extends AbstractConditionCalculatorConverter<IntegerCalculator, IntegerConditionCalculator> {

    @Override
    public IntegerConditionCalculator convert(IntegerCalculator source) {
        BeanEntity bean = source.getBean();
        return getCalculatorProvider().createCalculator(bean.getBeanName(), IntegerConditionCalculator.class);
    }
}