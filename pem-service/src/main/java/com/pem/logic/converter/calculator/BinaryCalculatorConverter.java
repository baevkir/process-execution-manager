package com.pem.logic.converter.calculator;

import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.logic.converter.common.AbstractConditionCalculatorConverter;
import com.pem.logic.converter.common.RegisterInConverterFactory;
import com.pem.persistence.api.model.calculator.bean.BinaryBeanConditionCalculator;
import com.pem.persistence.api.model.common.bean.BeanObject;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class BinaryCalculatorConverter extends AbstractConditionCalculatorConverter<BinaryBeanConditionCalculator, BinaryConditionCalculator> {

    @Override
    public BinaryConditionCalculator convert(BinaryBeanConditionCalculator source) {
        BeanObject bean = source.getBean();
        BinaryConditionCalculator calculator = getCalculatorProvider().createCalculator(bean.getBeanName(), BinaryConditionCalculator.class);
        calculator.setConditionCalculatorId(String.valueOf(source.getId()));
        return calculator;
    }
}
