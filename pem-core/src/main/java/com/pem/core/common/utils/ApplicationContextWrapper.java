package com.pem.core.common.utils;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
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

    public <T, A extends Annotation> Map<String, T> findBeanByAnnotation(final Class<A> annotation, Class<T> targetClass) {
        Assert.notNull(annotation);
        Assert.notNull(targetClass);
        Map<String, T> beans = applicationContext.getBeansOfType(targetClass, true, true);
        LOGGER.trace("Try to Find beans {} with annotation {}.", targetClass, annotation);

        return Maps.filterEntries(beans, new Predicate<Map.Entry<String, T>>() {
            @Override
            public boolean apply(Map.Entry<String, T> input) {
                T value = input.getValue();
                Class clazz = AopProxyUtils.ultimateTargetClass(value);
                return clazz.isAnnotationPresent(annotation);
            }
        });
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
