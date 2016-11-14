package com.pem.persistence.converter;


import com.pem.common.utils.ApplicationContextWrapper;
import com.pem.persistence.converter.common.Converter;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.MultiKeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ConverterFactoryImpl implements com.pem.persistence.converter.ConverterFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterFactoryImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    private MultiKeyMap convertersMap = new MultiKeyMap();

    @Override
    public <S, T> T convert(S source, Class<T> targetClass) {
        Converter<S, T> converter = getConverter((Class<S>) source.getClass(), targetClass);
        T result = converter.convert(source);
        LOGGER.trace("Conversion result for {} is: {}.", source.getClass(), result);
        return result;
    }

    @Override
    public <S, T> List<T> convertAll(List<S> sources, Class<T> targetClass) {
        LOGGER.trace("Start to convert to {}.", targetClass);
        if (CollectionUtils.isEmpty(sources)) {
            return Collections.EMPTY_LIST;
        }

        Class sClass = sources.iterator().next().getClass();
        Converter<S, T> converter = getConverter(sClass, targetClass);
        return converter.convertAll(sources);
    }

    private <S, T> Converter<S, T> getConverter(Class<S> sClass, Class<T> tClass) {
        Converter<S, T> converter = (Converter<S, T>) convertersMap.get(sClass, tClass);
        Assert.notNull(converter, String.format("Can't find Converter from %s to %s.", sClass, tClass));

        LOGGER.debug("Find converter for {} and {}: {}.", sClass, tClass, converter.getClass());
        return converter;
    }

    @PostConstruct
    public void initConverters(){
        Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class, true, true);
        ApplicationContextWrapper contextWrapper = new ApplicationContextWrapper(applicationContext);
        String currentFactoryName = contextWrapper.getBeanName(this);
        for (Map.Entry<String, Converter> converter : converters.entrySet()) {
            Converter value = converter.getValue();
            Class<? extends Converter> clazz = (Class<? extends Converter>) AopProxyUtils.ultimateTargetClass(value);

            if (clazz.isAnnotationPresent(RegisterInConverterFactory.class)) {
                RegisterInConverterFactory annotation = clazz.getAnnotation(RegisterInConverterFactory.class);
                String factoryName = annotation.factoryName();
                if (currentFactoryName.equals(factoryName)) {
                    setConverter(value);
                }
            }
        }
    }

    private <T, S> void setConverter(Converter<S, T> converter) {
        Class converterClass = converter.getClass();
        Class[] generics = GenericTypeResolver.resolveTypeArguments(converterClass, Converter.class);
        Class sClass = generics[0];
        Class tClass = generics[1];
        convertersMap.put(sClass, tClass, converter);
    }
}
