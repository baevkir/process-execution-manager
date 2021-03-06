package com.pem.core.common.converter.factory.impl;

import com.pem.core.common.bean.iterable.BeansIterable;
import com.pem.core.common.bean.iterable.Consumer;
import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.core.common.utils.ApplicationContextWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class AutoInitConverterFactory implements ConverterFactory, ApplicationContextAware, BeanNameAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoInitConverterFactory.class);
    private String converterFactoryName;
    private ApplicationContext applicationContext;

    @Override
    public <S, T> T convert(S source, Class<T> targetClass) {
        return convert(source, (Class<S>) source.getClass(), targetClass);
    }

    @Override
    public <S, T, O extends S> T convert(O source, Class<S> sourceClass, Class<T> targetClass) {
        Converter<S, T> converter = getConverter(sourceClass, targetClass);
        T result = converter.convert(source);
        LOGGER.trace("Conversion result for {} is: {}.", source.getClass(), result);
        return result;
    }

    @PostConstruct
    public void initConverters() {
        ApplicationContextWrapper contextWrapper = new ApplicationContextWrapper(applicationContext);
        Map<String, Converter> converters = contextWrapper.findBeanByAnnotation(RegisterInConverterFactory.class, Converter.class);

        BeansIterable.fromBeans(converters).forEach(new Consumer<Converter>() {
            @Override
            public void accept(Converter input) {
                Class<? extends Converter> clazz = (Class<? extends Converter>) AopProxyUtils.ultimateTargetClass(input);
                RegisterInConverterFactory annotation = clazz.getAnnotation(RegisterInConverterFactory.class);
                List<String> factories = Arrays.asList(annotation.factories());
                if (factories.contains(converterFactoryName)) {
                    setConverter(input);
                }
            }
        });
    }

    protected abstract  <T, S> void setConverter(Converter<S, T> converter);

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        converterFactoryName = name;
    }
}
