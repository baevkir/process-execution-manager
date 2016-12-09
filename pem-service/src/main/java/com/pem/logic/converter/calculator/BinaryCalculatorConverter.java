package com.pem.logic.converter.calculator;

import com.pem.core.calculator.BinaryCalculator;
import com.pem.logic.common.ServiceConstants;
import com.pem.logic.converter.common.AbstractConditionCalculatorConverter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.calculator.bean.BinaryBeanCalculatorDTO;
import com.pem.model.common.bean.BeanObject;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class BinaryCalculatorConverter extends AbstractConditionCalculatorConverter<BinaryBeanCalculatorDTO, BinaryCalculator> {

    @Override
    public BinaryCalculator convert(BinaryBeanCalculatorDTO source) {
        BeanObject bean = source.getBean();
        BinaryCalculator calculator = getCalculatorProvider().createCalculator(bean.getBeanName(), BinaryCalculator.class);
        calculator.setId(source.getId());
        return calculator;
    }
}
