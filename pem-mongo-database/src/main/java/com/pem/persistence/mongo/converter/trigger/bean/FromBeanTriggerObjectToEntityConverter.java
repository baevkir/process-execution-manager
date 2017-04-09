package com.pem.persistence.mongo.converter.trigger.bean;

import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.trigger.bean.BeanTriggerObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.calculator.bean.BeanTriggerEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromBeanTriggerObjectToEntityConverter extends ConverterTemplateMethods implements Converter<BeanTriggerObject, BeanTriggerEntity> {
    @Override
    public BeanTriggerEntity convert(BeanTriggerObject source) {
        BeanTriggerEntity triggerEntity = new BeanTriggerEntity();
        fillCommonFields(triggerEntity, source);
        triggerEntity.setActive(source.isActive());
        triggerEntity.setBean(source.getBean());

        return triggerEntity;
    }
}
