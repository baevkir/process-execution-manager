package com.pem.logic.common.context;

import com.google.common.base.Preconditions;
import com.pem.core.common.applicationcontext.bean.BeanObject;
import com.pem.core.context.OperationContext;
import com.pem.logic.bean.provider.BeanProvider;
import com.pem.logic.service.context.impl.OperationContextServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class OperationContextFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationContextServiceImpl.class);
    private BeanProvider beanProvider;
    private BigInteger id;
    private Supplier<Class<? extends OperationContext>> contextClassSupplier;
    private String operationContextBeanName;
    private Map<String, Object> contextParams = new HashMap<>();

    private OperationContextFactory(BeanProvider beanProvider) {
        this.beanProvider = Preconditions.checkNotNull(beanProvider, "BeanProvider is Null.");
    }

    public static OperationContextFactory create(BeanProvider beanProvider) {
        return new OperationContextFactory(beanProvider);
    }

    public Mono<OperationContext> createContext() {
        Assert.notNull(id, "Can't create context without ID.");
        return Mono.just(buildContext())
                .doOnNext(context -> context.setId(id))
                .doOnNext(context ->  copyParamsToContext(context));
    }

    public OperationContextFactory setContextClass(Class<? extends OperationContext> contextClass) {
        Assert.notNull(contextClass, "Can't set empty contextClass.");
        return setContextClass(() -> contextClass);
    }

    public OperationContextFactory setContextClass(String contextClassName) {
        Assert.hasText(contextClassName, "Can't set empty contextClass.");
        return setContextClass(() -> getContextClassByName(contextClassName));
    }

    public OperationContextFactory setContextClass(Supplier<Class<? extends OperationContext>> supplier) {
        Assert.notNull(supplier, "Can't set NULL Supplier.");
        this.contextClassSupplier = supplier;
        return this;
    }

    public OperationContextFactory setContextBeanName(String name){
        Assert.hasText(name, "Can't set empty ContextBeanName.");
        operationContextBeanName = name;
        return this;
    }

    public OperationContextFactory setBeanObject(BeanObject beanObject) {
        Assert.notNull(beanObject, "Can't set NULL BeanObject.");
        return setContextBeanName(beanObject.getBeanName());
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

    protected OperationContext buildContext() {
        if (StringUtils.isNotEmpty(operationContextBeanName)) {
            return beanProvider.createInstance(operationContextBeanName, OperationContext.class);
        }
        if (contextClassSupplier != null) {
           return createInstanceFromClass();
        }
        return beanProvider.createCommonInstance(OperationContext.class);
    }

    private void copyParamsToContext(OperationContext context) {
        contextParams.entrySet()
                .forEach(paramEntry -> context.setContextParam(paramEntry.getKey(), paramEntry.getValue()));
    }

    private Class<? extends OperationContext> getContextClassByName(String contextClassName) {
        try {
            LOGGER.debug("Try to find context class by name {},", contextClassName);
            Class clazz = Class.forName(contextClassName);
            Assert.isAssignable(OperationContext.class, clazz, "Class {} isn't inherit OperationContext");
            return clazz;
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    private OperationContext createInstanceFromClass() {
        try {
            Class<? extends OperationContext> contextClass = contextClassSupplier.get();
            Assert.notNull(contextClass, "Context class is null.");
            LOGGER.debug("Create operation context from class {}.", contextClass);
            return contextClass.newInstance();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
