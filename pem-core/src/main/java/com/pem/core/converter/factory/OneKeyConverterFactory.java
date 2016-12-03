package com.pem.core.converter.factory;

import com.pem.core.converter.impl.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class OneKeyConverterFactory extends ConverterFactoryImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterFactoryImpl.class);

    private Map<Class, Converter> converterMap = new HashMap<>();

    @Override
    public <S, T> Converter<S, T> getConverter(Class<S> sClass, Class<T> tClass) {
        Converter converter = converterMap.get(sClass);
        Assert.notNull(converter, String.format("Can't find Converter from %s to %s.", sClass, tClass));

        Class[] generics = GenericTypeResolver.resolveTypeArguments(converter.getClass(), Converter.class);
        Class clazz = generics[1];
        Assert.isAssignable(tClass, clazz, String.format("Can't find Converter from %s to %s.", sClass, tClass));

        LOGGER.debug("Find converter for {} and {}: {}.", sClass, tClass, converter.getClass());
        return converter;
    }

    @Override
    protected <T, S> void setConverter(Converter<S, T> converter) {
        Class converterClass = converter.getClass();
        Class[] generics = GenericTypeResolver.resolveTypeArguments(converterClass, Converter.class);
        Class sClass = generics[0];
        converterMap.put(sClass, converter);
    }
}
