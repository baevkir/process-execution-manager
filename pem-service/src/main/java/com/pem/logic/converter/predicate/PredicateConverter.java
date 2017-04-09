package com.pem.logic.converter.predicate;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.core.predicate.Predicate;
import com.pem.logic.bean.provider.predicate.PredicateProvider;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.predicate.bean.BeanPredicateObject;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class PredicateConverter implements Converter<BeanPredicateObject, Predicate> {
    private PredicateProvider predicateProvider;

    @Override
    public Predicate convert(BeanPredicateObject source) {
        BeanObject bean = source.getBean();
        Predicate predicate = predicateProvider.createPredicate(bean.getBeanName(), Predicate.class);
        predicate.setId(source.getId());
        return predicate;
    }

    public void setPredicateProvider(PredicateProvider predicateProvider) {
        this.predicateProvider = predicateProvider;
    }
}
