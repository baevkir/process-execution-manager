package com.pem.core.context;

import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class OperationContextFactory {
    private Class<? extends OperationContext> contextClass = OperationContextImpl.class;
    private Map<String, Object> contextParams = new HashMap<>();
    private BigInteger id;

    private OperationContextFactory() {
    }

    public static OperationContextFactory create() {
        return new OperationContextFactory();
    }

    public Mono<OperationContext> createContext() {
        Assert.notNull(contextClass, "Context class undefined.");
        Assert.notNull(id, "Can't create context without ID.");
        return Mono.just(createInstance())
                .doOnNext(context -> context.setId(id))
                .doOnNext(context ->  copyParamsToContext(context));
    }

    public OperationContextFactory setId(BigInteger id) {
        this.id = id;
        return this;
    }

    public OperationContextFactory setContextParam(String key, Object value) {
        Assert.hasText(key, "Can't set empty key.");
        contextParams.put(key, value);
        return this;
    }

    public OperationContextFactory setContextClass(Class<? extends OperationContext> contextClass) {
        Assert.notNull(contextClass, "Can't set empty contextClass.");
        Assert.isTrue(ClassUtils.hasConstructor(contextClass), String.format("Class %s does not have default constructor.", contextClass));
        this.contextClass = contextClass;
        return this;
    }

    public OperationContextFactory setContextClass(String contextClassName) {
        Assert.hasText(contextClassName, "Can't set empty contextClass.");
        return setContextClass(getContextClassByName(contextClassName));
    }

    private void copyParamsToContext(OperationContext context) {
        contextParams.entrySet()
                .forEach(paramEntry -> context.setContextParam(paramEntry.getKey(), paramEntry.getValue()));
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
