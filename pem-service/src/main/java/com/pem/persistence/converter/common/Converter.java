package com.pem.persistence.converter.common;

public interface Converter<S, T> {
    T convert(S source);
}
