package com.pem.operation.basic;

import com.pem.context.OperationContext;
import com.pem.operation.basic.reflection.ReflectionManager;
import com.pem.operation.basic.reflection.ReflectionManagerImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

public abstract class AnnotationOperation implements Operation {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationOperation.class);

    private ReflectionManager reflectionManager;

    @Override
    public void execute(OperationContext context) {
        LOGGER.trace("Start to execute Operation {}.", this.getClass());
        ReflectionManager reflectionManager = getReflectionManager();
        Method method = reflectionManager.getMethod(this.getClass());
        String resultParam = reflectionManager.getResultParam(method);

        Object[] args = reflectionManager.getArguments(method, context);
        ReflectionUtils.makeAccessible(method);
        Object result = ReflectionUtils.invokeMethod(method, this, args);
        if (StringUtils.isNotEmpty(resultParam)) {
            context.setContextParam(resultParam, result);
        }
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
