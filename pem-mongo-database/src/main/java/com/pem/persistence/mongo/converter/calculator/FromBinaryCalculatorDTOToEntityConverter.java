package com.pem.persistence.mongo.converter.calculator;

import com.pem.core.converter.impl.Converter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.calculator.bean.BinaryBeanConditionCalculatorDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.calculator.BinaryCalculator;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromBinaryCalculatorDTOToEntityConverter  extends ConverterTemplateMethods implements Converter<BinaryBeanConditionCalculatorDTO, BinaryCalculator> {
    @Override
    public BinaryCalculator convert(BinaryBeanConditionCalculatorDTO source) {
        BinaryCalculator binaryCalculator = new BinaryCalculator();
        fillCommonFields(binaryCalculator, source);

        binaryCalculator.setBean(source.getBean());

        return binaryCalculator;
    }
}
