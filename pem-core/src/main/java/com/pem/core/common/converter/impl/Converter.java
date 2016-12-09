package com.pem.core.common.converter.impl;

public interface Converter<S, T> {
    T convert(S source);
}
