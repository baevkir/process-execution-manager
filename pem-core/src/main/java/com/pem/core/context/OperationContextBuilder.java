package com.pem.core.context;

import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

public class OperationContextBuilder {
    private Class<? extends OperationContext> contextClass;
    private Map<String, Object> contextParams = new HashMap<>();

    private OperationContextBuilder() {
    }

    public static OperationContextBuilder newInstance() {
        return new OperationContextBuilder();
    }

    public OperationContext build() {
        Assert.notNull(contextClass, "Context class indefined.");
        OperationContext context = createInstance();
        for (Map.Entry<String, Object> paramEntry : contextParams.entrySet()) {
            context.setContextParam(paramEntry.getKey(), paramEntry.getValue());
        }

        return context;
    }

    public OperationContextBuilder setContextParam(String key, Object value) {
        Assert.hasText(key, "Can't set empty key.");
        contextParams.put(key, value);
        return this;
    }

    public OperationContextBuilder setContextClass(Class<? extends OperationContext> contextClass) {
        Assert.notNull(contextClass, "Can't set empty contextClass.");
        Assert.isTrue(ClassUtils.hasConstructor(contextClass), String.format("Class %s does not have default constructor.", contextClass));
        this.contextClass = contextClass;
        return this;
    }

    public void setContextClass(String contextClassName) {
        Assert.hasText(contextClassName, "Can't set empty contextClass.");
        setContextClass(getContextClassByName(contextClassName));
    }

    private Class<? extends OperationContext> getContextClassByName(String contextClassName) {
        try {
            Class clazz = Class.forName(contextClassName);
            Assert.isAssignable(OperationContext.class, clazz, "Class {} isn't inherit OperationContext");

            return clazz;
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    private OperationContext createInstance() {
        try {
            return contextClass.newInstance();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
