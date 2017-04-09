package com.pem.logic.converter.trigger;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.core.trigger.Trigger;
import com.pem.logic.bean.provider.trigger.TriggerProvider;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.trigger.bean.BeanTriggerObject;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class TriggerConverter implements Converter<BeanTriggerObject, Trigger> {

    private TriggerProvider triggerProvider;
    @Override
    public Trigger convert(BeanTriggerObject source) {
        BeanObject bean = source.getBean();
        Trigger trigger = triggerProvider.createTrigger(bean.getBeanName(), Trigger.class);
        trigger.setId(source.getId());
        return trigger;
    }

    public void setTriggerProvider(TriggerProvider triggerProvider) {
        this.triggerProvider = triggerProvider;
    }
}