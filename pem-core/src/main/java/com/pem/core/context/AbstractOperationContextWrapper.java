package com.pem.core.context;

import java.math.BigInteger;

public abstract class AbstractOperationContextWrapper implements OperationContext {
    private OperationContext context;

    public AbstractOperationContextWrapper(OperationContext context) {
        this.context = context;
    }

    @Override
    public BigInteger getId() {
        return context.getId();
    }

    @Override
    public void setId(BigInteger id) {
        context.setId(id);
    }

    @Override
    public <S> void setContextParam(String key, S value) {
        context.setContextParam(key, value);
    }

    @Override
    public <S> S getContextParam(String key, Class<S> clazz) {
        return context.getContextParam(key, clazz);
    }

    @Override
    public boolean isOpen() {
        return context.isOpen();
    }

    @Override
    public void open() {
        context.open();
    }

    @Override
    public void close() {
        context.close();
    }
}
