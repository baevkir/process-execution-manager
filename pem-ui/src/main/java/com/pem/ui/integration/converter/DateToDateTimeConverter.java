package com.pem.ui.integration.converter;

import com.vaadin.data.util.converter.Converter;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.Locale;

public class DateToDateTimeConverter implements Converter<Date, DateTime> {

    @Override
    public DateTime convertToModel(Date value, Class<? extends DateTime> targetType, Locale locale) throws ConversionException {
        if (value == null) {
            return null;
        }
        return new DateTime(value);
    }

    @Override
    public Date convertToPresentation(DateTime value, Class<? extends Date> targetType, Locale locale) throws ConversionException {
        if (value == null) {
            return null;
        }
        return value.toDate();
    }

    @Override
    public Class<DateTime> getModelType() {
        return DateTime.class;
    }

    @Override
    public Class<Date> getPresentationType() {
        return Date.class;
    }
}
