package com.pem.persistence.mongo.converter.trigger.bean;

import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.trigger.bean.BeanTriggerObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.calculator.bean.BeanTriggerEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromBeanTriggerEntityToObjectConverter extends ConverterTemplateMethods implements Converter<BeanTriggerEntity, BeanTriggerObject> {

    @Override
    public BeanTriggerObject convert(BeanTriggerEntity source) {
        BeanTriggerObject conditionCalculatorDTO = new BeanTriggerObject();
        fillCommonFields(conditionCalculatorDTO, source);
        conditionCalculatorDTO.setActive(source.isActive());
        conditionCalculatorDTO.setBean(source.getBean());

        return conditionCalculatorDTO;
    }
}
