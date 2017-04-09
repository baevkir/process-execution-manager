package com.pem.logic.bean.provider.trigger;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.trigger.Trigger;

import java.util.Set;

public interface TriggerProvider {
    <T extends Trigger> T createTrigger(String beanName, Class<T> triggerClass);
    Set<BeanObject> getAllTriggerBeanObjects();
}
