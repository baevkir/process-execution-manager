package com.pem.logic.bean.provider.trigger.impl;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.bean.BeanObjectBuilder;
import com.pem.core.common.bean.BeansStream;
import com.pem.core.common.utils.ApplicationContextWrapper;
import com.pem.core.trigger.Trigger;
import com.pem.logic.bean.provider.trigger.TriggerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TriggerProviderImpl implements TriggerProvider, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerProviderImpl.class);

    private ApplicationContextWrapper contextWrapper;

    @Override
    public <T extends Trigger> T createTrigger(String beanName, Class<T> triggerClass) {
        return contextWrapper.getPrototypeBeanByType(beanName, triggerClass);
    }

    @Override
    public Set<BeanObject> getAllTriggerBeanObjects() {
        LOGGER.debug("Get All Triggers.");
        final String applicationId = contextWrapper.getApplicationContext().getId();
        Map<String, Trigger> beans = contextWrapper.findBeansByAnnotation(GlobalTrigger.class, Trigger.class);

        return BeansStream.fromBeans(beans)
                .filterWithAnnotation(GlobalTrigger.class, annotation -> checkAnnotation(annotation, applicationId))
                .transform(triggerEntry -> transformToBeanObject(triggerEntry))
                .collect(Collectors.toSet());
    }

    private boolean checkAnnotation(GlobalTrigger annotation, String applicationId) {
        if (annotation.all()) {
            return true;
        }
        List<String> executors = Arrays.asList(annotation.executors());
        return executors.contains(applicationId);
    }

    private BeanObject transformToBeanObject(BeansStream.Entry<Trigger> operationEntry) {
        return BeanObjectBuilder.newInstance()
                .setBeanName(operationEntry.getBeanName())
                .setName(operationEntry.getBeanAnnotation(GlobalTrigger.class).get().value())
                .build();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.contextWrapper = new ApplicationContextWrapper(applicationContext);
    }
}
