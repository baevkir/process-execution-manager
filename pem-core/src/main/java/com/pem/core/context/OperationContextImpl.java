package com.pem.core.context;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class OperationContextImpl implements OperationContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationContextImpl.class);
    private boolean isOpen = false;
    private Map<String, Object> contextParams = new HashMap<>();
    private String id;
    @Override
    public String getContextId() {
        return id;
    }

    @Override
    public void setContextId(String id) {
        this.id = id;
    }

    @Override
    public <S> void setContextParam(String key, S value) {
        Assert.isTrue(StringUtils.isNotEmpty(key), "Can't set empty key.");

        LOGGER.trace("Set param. Key: {}, Value: {}.", key, value);
        contextParams.put(key, value);
    }

    @Override
    public <S> S getContextParam(String key, Class<S> clazz) {
        Assert.isTrue(StringUtils.isNotEmpty(key), "Can't get para for empty key.");

        LOGGER.trace("Get param fer Key: {}, Value: {}.", key);
        Object value = contextParams.get(key);
        if (value == null) {
            return null;
        }

        return clazz.cast(value);
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void open() {
        Assert.isTrue(!isOpen, "Context already opened.");
        isOpen = true;
    }

    @Override
    public void close() {
        Assert.isTrue(isOpen, "Can't close closed context.");
        isOpen = false;
    }
}
