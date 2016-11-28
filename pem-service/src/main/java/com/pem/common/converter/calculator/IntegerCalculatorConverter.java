package com.pem.common.converter.calculator;

import com.pem.conditioncalculator.IntegerConditionCalculator;
import com.pem.common.converter.common.AbstractConditionCalculatorConverter;
import com.pem.common.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.calculator.IntegerCalculator;
import com.pem.persistence.model.common.bean.BeanEntity;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class IntegerCalculatorConverter extends AbstractConditionCalculatorConverter<IntegerCalculator, IntegerConditionCalculator> {

    @Override
    public IntegerConditionCalculator convert(IntegerCalculator source) {
        BeanEntity bean = source.getBean();
        IntegerConditionCalculator calculator = getCalculatorProvider().createCalculator(bean.getBeanName(), IntegerConditionCalculator.class);
        calculator.setConditionCalculatorId(String.valueOf(source.getId()));
        return calculator;
    }
}