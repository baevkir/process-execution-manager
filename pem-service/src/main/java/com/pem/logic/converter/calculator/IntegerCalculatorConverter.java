package com.pem.logic.converter.calculator;

import com.pem.core.calculator.IntegerCalculator;
import com.pem.logic.common.ServiceConstants;
import com.pem.logic.converter.common.AbstractConditionCalculatorConverter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.calculator.bean.IntegerBeanCalculatorDTO;
import com.pem.model.common.bean.BeanObject;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class IntegerCalculatorConverter extends AbstractConditionCalculatorConverter<IntegerBeanCalculatorDTO, IntegerCalculator> {

    @Override
    public IntegerCalculator convert(IntegerBeanCalculatorDTO source) {
        BeanObject bean = source.getBean();
        IntegerCalculator calculator = getCalculatorProvider().createCalculator(bean.getBeanName(), IntegerCalculator.class);
        calculator.setId(source.getId());
        return calculator;
    }
}