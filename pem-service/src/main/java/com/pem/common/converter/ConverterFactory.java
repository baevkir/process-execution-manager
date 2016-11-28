package com.pem.common.converter;

public interface ConverterFactory {
    <S, T> T convert(S source, Class<T> targetClass);
    <S, T, O extends S> T convert(O source, Class<S> sourceClass, Class<T> targetClass);
}
