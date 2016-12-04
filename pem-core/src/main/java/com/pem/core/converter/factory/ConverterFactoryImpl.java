package com.pem.core.converter.factory;


import com.pem.core.common.utils.ApplicationContextWrapper;
import com.pem.core.converter.impl.Converter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import org.apache.commons.collections.map.MultiKeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ConverterFactoryImpl implements ConverterFactory, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterFactoryImpl.class);

    private ApplicationContext applicationContext;

    private MultiKeyMap convertersMap = new MultiKeyMap();

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
        Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class, false, true);
        ApplicationContextWrapper contextWrapper = new ApplicationContextWrapper(applicationContext);
        String currentFactoryName = contextWrapper.getBeanName(this);
        for (Map.Entry<String, Converter> converter : converters.entrySet()) {
            Converter value = converter.getValue();
            Class<? extends Converter> clazz = (Class<? extends Converter>) AopProxyUtils.ultimateTargetClass(value);

            if (clazz.isAnnotationPresent(RegisterInConverterFactory.class)) {
                RegisterInConverterFactory annotation = clazz.getAnnotation(RegisterInConverterFactory.class);
                List<String> factories = Arrays.asList(annotation.factories());
                if (factories.contains(currentFactoryName)) {
                    setConverter(value);
                }
            }
        }
    }

    @Override
    public <S, T> Converter<S, T> getConverter(Class<S> sClass, Class<T> tClass) {
        Converter<S, T> converter = (Converter<S, T>) convertersMap.get(sClass, tClass);
        Assert.notNull(converter, String.format("Can't find Converter from %s to %s.", sClass, tClass));

        LOGGER.debug("Find converter for {} and {}: {}.", sClass, tClass, converter.getClass());
        return converter;
    }

    protected <T, S> void setConverter(Converter<S, T> converter) {
        Class converterClass = converter.getClass();
        Class[] generics = GenericTypeResolver.resolveTypeArguments(converterClass, Converter.class);
        Class sClass = generics[0];
        Class tClass = generics[1];
        convertersMap.put(sClass, tClass, converter);
    }

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
