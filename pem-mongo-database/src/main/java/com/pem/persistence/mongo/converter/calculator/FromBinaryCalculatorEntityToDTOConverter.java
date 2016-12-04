package com.pem.persistence.mongo.converter.calculator;

import com.pem.core.converter.impl.Converter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.calculator.bean.BinaryBeanConditionCalculatorDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.calculator.BinaryCalculator;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromBinaryCalculatorEntityToDTOConverter extends ConverterTemplateMethods implements Converter<BinaryCalculator, BinaryBeanConditionCalculatorDTO> {

    @Override
    public BinaryBeanConditionCalculatorDTO convert(BinaryCalculator source) {
        BinaryBeanConditionCalculatorDTO conditionCalculatorDTO = new BinaryBeanConditionCalculatorDTO();
        fillCommonFields(conditionCalculatorDTO, source);

        conditionCalculatorDTO.setBean(source.getBean());

        return conditionCalculatorDTO;
    }
}
