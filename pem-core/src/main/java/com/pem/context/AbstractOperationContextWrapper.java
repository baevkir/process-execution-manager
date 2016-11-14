package com.pem.context;

public abstract class AbstractOperationContextWrapper implements OperationContext {
    private OperationContext context;

    public AbstractOperationContextWrapper(OperationContext context) {
        this.context = context;
    }

    @Override
    public String getContextId() {
        return context.getContextId();
    }

    @Override
    public void setContextId(String id) {
        context.setContextId(id);
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
