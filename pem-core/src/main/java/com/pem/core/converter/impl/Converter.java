package com.pem.core.converter.impl;

public interface Converter<S, T> {
    T convert(S source);
}
