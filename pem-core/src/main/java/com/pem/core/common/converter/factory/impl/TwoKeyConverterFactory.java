package com.pem.core.common.converter.factory.impl;


import com.pem.core.common.converter.impl.Converter;
import org.apache.commons.collections.map.MultiKeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.Assert;

public class TwoKeyConverterFactory extends AutoInitConverterFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwoKeyConverterFactory.class);

    private MultiKeyMap convertersMap = new MultiKeyMap();

    @Override
    public <S, T> Converter<S, T> getConverter(Class<S> sClass, Class<T> tClass) {
        Converter<S, T> converter = (Converter<S, T>) convertersMap.get(sClass, tClass);
        Assert.notNull(converter, String.format("Can't find Converter from %s to %s.", sClass, tClass));

        LOGGER.debug("Find converter for {} and {}: {}.", sClass, tClass, converter.getClass());
        return converter;
    }

    @Override
    protected <T, S> void setConverter(Converter<S, T> converter) {
        Class converterClass = converter.getClass();
        Class[] generics = GenericTypeResolver.resolveTypeArguments(converterClass, Converter.class);
        Class sClass = generics[0];
        Class tClass = generics[1];
        convertersMap.put(sClass, tClass, converter);
    }
}
