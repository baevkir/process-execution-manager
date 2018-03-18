package com.pem.persistence.mongo.converter.predicate.bean;

import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.model.predicate.bean.BeanPredicateObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.predicate.bean.BeanPredicateEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromBeanPredicateEntityToObjectConverter extends ConverterTemplateMethods implements Converter<BeanPredicateEntity, BeanPredicateObject> {

    @Override
    public BeanPredicateObject convert(BeanPredicateEntity source) {
        BeanPredicateObject conditionCalculatorDTO = new BeanPredicateObject();
        fillCommonFields(conditionCalculatorDTO, source);
        conditionCalculatorDTO.setActive(source.isActive());
        conditionCalculatorDTO.setBean(source.getBean());

        return conditionCalculatorDTO;
    }
}
