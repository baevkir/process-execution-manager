package com.pem.core.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContextWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextWrapper.class);

    private ApplicationContext applicationContext;

    public ApplicationContextWrapper(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public <T> Map<String, T> findBeanByAnnotation(Class<? extends Annotation> annotation, Class<T> targetClass) {
        Map<String, T> result = new HashMap<>();
        Map<String, T> beans = applicationContext.getBeansOfType(targetClass, true, true);
        LOGGER.trace("Try to Find beans {} with annotation {}.", targetClass, annotation);

        for (Map.Entry<String, T> entry : beans.entrySet()) {
            String key = entry.getKey();
            T value = entry.getValue();

            Class clazz = AopProxyUtils.ultimateTargetClass(value);
            if (clazz.isAnnotationPresent(annotation)) {
                result.put(key, value);
            }
        }
        return result;
    }

    public <T> T getPrototypeBeanByType(String beanName, Class<T> type) {
        LOGGER.trace("Try to Find bean {} with type {}.", beanName, type);

        Assert.isTrue(applicationContext.containsBean(beanName), String.format("Context doesn't contains bean wih name %s", beanName));
        Assert.isTrue(applicationContext.isPrototype(beanName), String.format("Can't have scope different from 'Prototype' for bean %s.", beanName));

        Object bean = applicationContext.getBean(beanName);
        Assert.isInstanceOf(type, bean, String.format("Bean %s is not Instance Of %s", beanName, type));

        return type.cast(bean);
    }

    public <B> String getBeanName(B bean) {
        Class<B> beanClass = (Class<B>) bean.getClass();
        Map<String, B> beans = applicationContext.getBeansOfType(beanClass, true, true);
        for (Map.Entry<String, B> entry : beans.entrySet()) {
            if (entry.getValue() == bean) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("Can't find currant Bean name.");
    }
}
