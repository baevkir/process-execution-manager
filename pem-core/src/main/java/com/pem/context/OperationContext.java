package com.pem.context;

public interface OperationContext {
    <S> void setContextParam(String key, S value);
    <S> S getContextParam(String key, Class<S> clazz);
    boolean isOpen();
    void open();
    void close();
}
