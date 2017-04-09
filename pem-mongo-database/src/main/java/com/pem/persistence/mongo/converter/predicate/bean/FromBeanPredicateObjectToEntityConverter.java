package com.pem.persistence.mongo.converter.predicate.bean;

import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.predicate.bean.BeanPredicateObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.predicate.bean.BeanPredicateEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromBeanPredicateObjectToEntityConverter extends ConverterTemplateMethods implements Converter<BeanPredicateObject, BeanPredicateEntity> {
    @Override
    public BeanPredicateEntity convert(BeanPredicateObject source) {
        BeanPredicateEntity binaryCalculator = new BeanPredicateEntity();
        fillCommonFields(binaryCalculator, source);
        binaryCalculator.setActive(source.isActive());
        binaryCalculator.setBean(source.getBean());

        return binaryCalculator;
    }


}
