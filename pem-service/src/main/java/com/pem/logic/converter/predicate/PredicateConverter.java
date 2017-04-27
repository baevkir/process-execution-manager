package com.pem.logic.converter.predicate;

import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.core.predicate.Predicate;
import com.pem.logic.bean.provider.BeanProvider;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.predicate.bean.BeanPredicateObject;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class PredicateConverter implements Converter<BeanPredicateObject, Predicate> {
    private BeanProvider beanProvider;

    @Override
    public Predicate convert(BeanPredicateObject source) {
        Predicate predicate = beanProvider.createInstance(source.getBean(), Predicate.class);
        predicate.setId(source.getId());
        return predicate;
    }

    public void setBeanProvider(BeanProvider beanProvider) {
        this.beanProvider = beanProvider;
    }
}
