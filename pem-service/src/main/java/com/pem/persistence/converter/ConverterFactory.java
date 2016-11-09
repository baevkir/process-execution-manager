package com.pem.persistence.converter;

import java.util.List;

public interface ConverterFactory {
    <S, T> T convert(S source, Class<T> targetClass);
    <S, T> List<T> convertAll(List<S> source, Class<T> targetClass);
}
