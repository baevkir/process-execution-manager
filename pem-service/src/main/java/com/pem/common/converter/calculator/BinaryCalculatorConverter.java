package com.pem.common.converter.calculator;

import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.common.converter.common.AbstractConditionCalculatorConverter;
import com.pem.common.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.calculator.BinaryCalculator;
import com.pem.persistence.model.common.bean.BeanEntity;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class BinaryCalculatorConverter extends AbstractConditionCalculatorConverter<BinaryCalculator, BinaryConditionCalculator> {

    @Override
    public BinaryConditionCalculator convert(BinaryCalculator source) {
        BeanEntity bean = source.getBean();
        BinaryConditionCalculator calculator = getCalculatorProvider().createCalculator(bean.getBeanName(), BinaryConditionCalculator.class);
        calculator.setConditionCalculatorId(String.valueOf(source.getId()));
        return calculator;
    }
}
