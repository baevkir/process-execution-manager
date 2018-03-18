package com.pem.core.common.converter;

public interface Converter<S, T> {
    T convert(S source);
}
