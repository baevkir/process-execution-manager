package com.pem.persistence.mongo.converter.calculator.bean;

import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.calculator.bean.IntegerBeanCalculatorDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.calculator.bean.IntegerBeanCalculatorEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromIntegerBeanCalculatorEntityToDTOConverter extends ConverterTemplateMethods implements Converter<IntegerBeanCalculatorEntity, IntegerBeanCalculatorDTO> {

    @Override
    public IntegerBeanCalculatorDTO convert(IntegerBeanCalculatorEntity source) {
        IntegerBeanCalculatorDTO conditionCalculatorDTO = new IntegerBeanCalculatorDTO();
        fillCommonFields(conditionCalculatorDTO, source);
        conditionCalculatorDTO.setActive(source.isActive());
        conditionCalculatorDTO.setBean(source.getBean());

        return conditionCalculatorDTO;
    }
}
