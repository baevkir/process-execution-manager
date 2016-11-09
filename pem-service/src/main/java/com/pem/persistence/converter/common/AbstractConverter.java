package com.pem.persistence.converter.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConverter<S, T> implements Converter<S, T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConverter.class);

    @Override
    public List<T> convertAll(List<S> sources) {
        List<T> target = new ArrayList<>();
        for (S source : sources) {
            target.add(convert(source));
        }
        return target;
    }

}
