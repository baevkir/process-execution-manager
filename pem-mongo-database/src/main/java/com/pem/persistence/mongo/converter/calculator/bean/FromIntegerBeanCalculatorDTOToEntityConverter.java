package com.pem.persistence.mongo.converter.calculator.bean;

import com.pem.core.converter.impl.Converter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.calculator.bean.IntegerBeanCalculatorDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.calculator.bean.IntegerBeanCalculatorEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromIntegerBeanCalculatorDTOToEntityConverter extends ConverterTemplateMethods implements Converter<IntegerBeanCalculatorDTO, IntegerBeanCalculatorEntity> {
    @Override
    public IntegerBeanCalculatorEntity convert(IntegerBeanCalculatorDTO source) {
        IntegerBeanCalculatorEntity binaryCalculator = new IntegerBeanCalculatorEntity();
        fillCommonFields(binaryCalculator, source);

        binaryCalculator.setBean(source.getBean());

        return binaryCalculator;
    }
}
