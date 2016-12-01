package com.pem.logic.converter.calculator;

import com.pem.conditioncalculator.IntegerConditionCalculator;
import com.pem.logic.converter.common.AbstractConditionCalculatorConverter;
import com.pem.logic.converter.common.RegisterInConverterFactory;
import com.pem.model.calculator.bean.IntegerBeanConditionCalculatorDTO;
import com.pem.model.common.bean.BeanObject;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class IntegerCalculatorConverter extends AbstractConditionCalculatorConverter<IntegerBeanConditionCalculatorDTO, IntegerConditionCalculator> {

    @Override
    public IntegerConditionCalculator convert(IntegerBeanConditionCalculatorDTO source) {
        BeanObject bean = source.getBean();
        IntegerConditionCalculator calculator = getCalculatorProvider().createCalculator(bean.getBeanName(), IntegerConditionCalculator.class);
        calculator.setConditionCalculatorId(String.valueOf(source.getId()));
        return calculator;
    }
}