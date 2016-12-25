package com.pem.ui.integration.converter;

import com.pem.core.common.converter.factory.impl.TwoKeyConverterFactory;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.ConverterFactory;
import org.apache.commons.collections.map.MultiKeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;

import java.util.Set;

public class PemConverterFactory implements ConverterFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwoKeyConverterFactory.class);

    private MultiKeyMap convertersMap = new MultiKeyMap();

    @Override
    public <PRESENTATION, MODEL> Converter<PRESENTATION, MODEL> createConverter(Class<PRESENTATION> presentationType, Class<MODEL> modelType) {
        Converter<PRESENTATION, MODEL> converter = (Converter<PRESENTATION, MODEL>) convertersMap.get(presentationType, modelType);

        LOGGER.debug("Find converter for {} and {}: {}.", presentationType, modelType, converter);
        return converter;
    }

    public void setConverters(Set<Converter> converters) {
        for (Converter converter : converters) {
            setConverter(converter);
        }
    }

    private <PRESENTATION, MODEL> void setConverter(Converter<PRESENTATION, MODEL> converter) {
        Class converterClass = converter.getClass();
        Class[] generics = GenericTypeResolver.resolveTypeArguments(converterClass, Converter.class);
        Class<PRESENTATION> presentationType = generics[0];
        Class<MODEL> modelType = generics[1];
        convertersMap.put(presentationType, modelType, converter);
    }
}
