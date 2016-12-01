package com.pem.logic.converter.calculator;

import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.logic.converter.common.AbstractConditionCalculatorConverter;
import com.pem.logic.converter.common.RegisterInConverterFactory;
import com.pem.model.calculator.bean.BinaryBeanConditionCalculatorDTO;
import com.pem.model.common.bean.BeanObject;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class BinaryCalculatorConverter extends AbstractConditionCalculatorConverter<BinaryBeanConditionCalculatorDTO, BinaryConditionCalculator> {

    @Override
    public BinaryConditionCalculator convert(BinaryBeanConditionCalculatorDTO source) {
        BeanObject bean = source.getBean();
        BinaryConditionCalculator calculator = getCalculatorProvider().createCalculator(bean.getBeanName(), BinaryConditionCalculator.class);
        calculator.setConditionCalculatorId(String.valueOf(source.getId()));
        return calculator;
    }
}