package com.pem.logic.converter.calculator;

import com.pem.core.conditioncalculator.BinaryConditionCalculator;
import com.pem.logic.common.ServiceConstants;
import com.pem.logic.converter.common.AbstractConditionCalculatorConverter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.calculator.bean.BinaryBeanCalculatorDTO;
import com.pem.model.common.bean.BeanObject;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class BinaryCalculatorConverter extends AbstractConditionCalculatorConverter<BinaryBeanCalculatorDTO, BinaryConditionCalculator> {

    @Override
    public BinaryConditionCalculator convert(BinaryBeanCalculatorDTO source) {
        BeanObject bean = source.getBean();
        BinaryConditionCalculator calculator = getCalculatorProvider().createCalculator(bean.getBeanName(), BinaryConditionCalculator.class);
        calculator.setConditionCalculatorId(String.valueOf(source.getId()));
        return calculator;
    }
}
