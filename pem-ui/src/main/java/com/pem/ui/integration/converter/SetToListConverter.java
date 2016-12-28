package com.pem.ui.integration.converter;

import com.google.common.collect.FluentIterable;
import com.vaadin.data.util.converter.Converter;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SetToListConverter implements Converter<Set, List> {

    @Override
    public List convertToModel(Set value, Class<? extends List> targetType, Locale locale) throws ConversionException {
        if (value == null) {
            return null;
        }
        return FluentIterable.from(value).toList();
    }

    @Override
    public Set convertToPresentation(List value, Class<? extends Set> targetType, Locale locale) throws ConversionException {
        if (value == null) {
            return null;
        }
        return FluentIterable.from(value).toSet();
    }

    @Override
    public Class<List> getModelType() {
        return List.class;
    }

    @Override
    public Class<Set> getPresentationType() {
        return Set.class;
    }
}
