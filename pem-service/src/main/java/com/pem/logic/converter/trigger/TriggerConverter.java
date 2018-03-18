package com.pem.logic.converter.trigger;

import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.core.trigger.Trigger;
import com.pem.logic.bean.provider.BeanProvider;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.trigger.bean.BeanTriggerObject;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class TriggerConverter implements Converter<BeanTriggerObject, Trigger> {

    private BeanProvider beanProvider;
    @Override
    public Trigger convert(BeanTriggerObject source) {
        Trigger trigger = beanProvider.createInstance(source.getBean(), Trigger.class);
        trigger.setId(source.getId());
        return trigger;
    }

    public void setBeanProvider(BeanProvider beanProvider) {
        this.beanProvider = beanProvider;
    }
}