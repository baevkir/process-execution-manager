package com.pem.core.context;


import com.pem.core.common.Identifiable;

public interface OperationContext extends Identifiable {
    <S> void setContextParam(String key, S value);
    <S> S getContextParam(String key, Class<S> clazz);
    boolean isOpen();
    void open();
    void close();
}
