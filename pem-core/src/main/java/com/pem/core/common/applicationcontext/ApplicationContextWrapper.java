package com.pem.core.common.applicationcontext;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class ApplicationContextWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextWrapper.class);

    private ApplicationContext applicationContext;

    public ApplicationContextWrapper(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public Optional<ApplicationContextWrapper> getParent() {
        return Optional.ofNullable(applicationContext.getParent())
                .map(parentContext -> new ApplicationContextWrapper(parentContext));
    }

    public <T, A extends Annotation> Map<String, T> findBeansByAnnotation(final Class<A> annotation, Class<T> targetClass) {
        return findBeansByAnnotation(annotation, targetClass, null);
    }

    public <T, A extends Annotation> Map<String, T> findBeansByAnnotation(Class<A> annotation, Class<T> targetClass, Predicate<T> predicate) {
        Assert.notNull(annotation);
        Assert.notNull(targetClass);
        Map<String, T> beans = new HashMap<>();
        getParent().ifPresent(parentContextWrapper -> beans.putAll(parentContextWrapper.findBeansByAnnotation(annotation, targetClass)));
        beans.putAll(applicationContext.getBeansOfType(targetClass, true, true));
        LOGGER.trace("Try to Find beans {} with annotation {}.", targetClass, annotation);

        return Maps.filterEntries(beans, input -> {
            T value = input.getValue();
            Class clazz = AopProxyUtils.ultimateTargetClass(value);
            if (!clazz.isAnnotationPresent(annotation)) {
                return false;
            }
            if (predicate == null) {
                return true;
            }

            return predicate.test(input.getValue());
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
        return beans.entrySet().stream()
                .filter(entry -> entry.getValue() == bean)
                .map(entry -> entry.getKey())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find currant Bean name."));
    }
}
