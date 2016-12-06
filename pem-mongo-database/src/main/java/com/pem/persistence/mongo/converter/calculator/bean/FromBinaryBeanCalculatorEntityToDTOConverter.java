package com.pem.persistence.mongo.converter.calculator.bean;

import com.pem.core.converter.impl.Converter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.calculator.bean.BinaryBeanCalculatorDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.calculator.bean.BinaryBeanCalculatorEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromBinaryBeanCalculatorEntityToDTOConverter extends ConverterTemplateMethods implements Converter<BinaryBeanCalculatorEntity, BinaryBeanCalculatorDTO> {

    @Override
    public BinaryBeanCalculatorDTO convert(BinaryBeanCalculatorEntity source) {
        BinaryBeanCalculatorDTO conditionCalculatorDTO = new BinaryBeanCalculatorDTO();
        fillCommonFields(conditionCalculatorDTO, source);
        conditionCalculatorDTO.setActive(source.isActive());
        conditionCalculatorDTO.setBean(source.getBean());

        return conditionCalculatorDTO;
    }
}
