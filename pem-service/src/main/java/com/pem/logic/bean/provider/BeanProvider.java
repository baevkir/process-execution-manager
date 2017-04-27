package com.pem.logic.bean.provider;

import com.pem.core.common.bean.BeanObject;

import java.util.Set;

public interface BeanProvider {
    <O> O createInstance(String beanName, Class<O> type);
    <O> O createInstance(BeanObject beanObject, Class<O> type);
    Set<BeanObject> getAllForType(Class type);
}
