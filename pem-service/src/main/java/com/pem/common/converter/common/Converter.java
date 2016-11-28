package com.pem.common.converter.common;

public interface Converter<S, T> {
    T convert(S source);
}
