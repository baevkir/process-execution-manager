package com.pem.logic.converter.common;

public interface Converter<S, T> {
    T convert(S source);
}
