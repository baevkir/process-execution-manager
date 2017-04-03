package com.pem.core.operation.basic.util;

import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.basic.util.reflection.ReflectionManager;
import com.pem.core.operation.basic.util.reflection.ReflectionManagerImpl;
import com.pem.core.operation.basic.AbstractOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;

public abstract class AnnotationOperation extends AbstractOperation implements Operation {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationOperation.class);

    private ReflectionManager reflectionManager;

    @Override
    public Mono<OperationContext> execute(Mono<OperationContext> context) {
        LOGGER.trace("Start to execute Operation {}.", this.getClass());
        return context.doOnNext(operationContext -> {
            ReflectionManager reflectionManager = getReflectionManager();
            Method method = reflectionManager.getMethod(this.getClass());
            String resultParam = reflectionManager.getResultParam(method);

            Object[] args = reflectionManager.getArguments(method, operationContext);
            ReflectionUtils.makeAccessible(method);
            Object result = ReflectionUtils.invokeMethod(method, this, args);
            if (StringUtils.isNotEmpty(resultParam)) {
                operationContext.setContextParam(resultParam, result);
            }
        });

    }

    protected ReflectionManager createReflectionManager() {
        return new ReflectionManagerImpl();
    }

    private ReflectionManager getReflectionManager() {
        if (reflectionManager == null) {
            reflectionManager = createReflectionManager();
        }
        return reflectionManager;
    }
}
