package com.pem.core.common.converter.factory;


import com.pem.core.common.converter.impl.Converter;

public interface ConverterFactory {
    <S, T> T convert(S source, Class<T> targetClass);
    <S, T, O extends S> T convert(O source, Class<S> sourceClass, Class<T> targetClass);
    <S, T> Converter<S, T> getConverter(Class<S> sClass, Class<T> tClass);
}
