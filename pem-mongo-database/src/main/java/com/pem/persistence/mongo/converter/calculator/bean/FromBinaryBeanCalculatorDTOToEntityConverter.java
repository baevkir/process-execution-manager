package com.pem.persistence.mongo.converter.calculator.bean;

import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.calculator.bean.BinaryBeanCalculatorDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.calculator.bean.BinaryBeanCalculatorEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromBinaryBeanCalculatorDTOToEntityConverter extends ConverterTemplateMethods implements Converter<BinaryBeanCalculatorDTO, BinaryBeanCalculatorEntity> {
    @Override
    public BinaryBeanCalculatorEntity convert(BinaryBeanCalculatorDTO source) {
        BinaryBeanCalculatorEntity binaryCalculator = new BinaryBeanCalculatorEntity();
        fillCommonFields(binaryCalculator, source);
        binaryCalculator.setActive(source.isActive());
        binaryCalculator.setBean(source.getBean());

        return binaryCalculator;
    }


}
