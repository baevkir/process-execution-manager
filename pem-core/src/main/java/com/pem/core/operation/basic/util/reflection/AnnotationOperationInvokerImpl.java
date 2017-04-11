package com.pem.core.operation.basic.util.reflection;

import com.google.common.base.Preconditions;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Parameter;
import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.util.AnnotationOperation;
import com.pem.core.operation.basic.util.reflection.annotions.OperationMethod;
import com.pem.core.operation.basic.util.reflection.annotions.Param;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class AnnotationOperationInvokerImpl implements AnnotationOperationInvoker {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationOperationInvokerImpl.class);

    private AnnotationOperation operation;

    public AnnotationOperationInvokerImpl(AnnotationOperation operation) {
        this.operation = Preconditions.checkNotNull(operation, "Cannot create Invoker for empty operation");
        LOGGER.trace("Create new AnnotationOperationInvoker for operation {} {}.", operation.getClass(), operation.getId());
    }

    @Override
    public OperationContext invoke(OperationContext context) {
        fillOperationParameters(context);
        Method invokerMethod = getMethod(operation.getClass());
        Object[] arguments = getArguments(invokerMethod, context);
        Object result = ReflectionUtils.invokeMethod(invokerMethod, operation, arguments);

        String resultParam = getResultParam(invokerMethod);
        if (StringUtils.isNotEmpty(resultParam)) {
            context.setContextParam(resultParam, result);
        }
        return context;
    }

    private void fillOperationParameters(OperationContext context) {
        Arrays.stream(operation.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Param.class))
                .peek(field -> ReflectionUtils.makeAccessible(field))
                .forEach(field -> {
                    Object value = getParamFromContext(field.getAnnotation(Param.class), context);
                    ReflectionUtils.setField(field, operation, value);
                });
    }

    private Method getMethod(Class clazz) {
        LOGGER.trace("Start to find OperationMethod in class {}.", clazz);
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(OperationMethod.class))
                .peek(method -> LOGGER.debug("Find OperationMethod {} for class {}.", method, clazz))
                .peek(method -> ReflectionUtils.makeAccessible(method))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Can't execute %s. There is no class annotated @OperationMethod.", clazz)));
    }


    private Object[] getArguments(Method method, OperationContext context) {
        return Invokable.from(method).getParameters().stream()
                .map(parameter -> getArgFromContext(context, parameter))
                .toArray();
    }

    private String getResultParam(Method method) {
        OperationMethod annotation = method.getAnnotation(OperationMethod.class);
        return annotation.result();
    }

    private Object getArgFromContext(OperationContext context, Parameter parameter) {
        if (!parameter.isAnnotationPresent(Param.class)) {
            throw new NullPointerException("Parameter " + parameter + " does not market with annotation @Param.");
        }

        Param annotation = parameter.getAnnotation(Param.class);
        return getParamFromContext(annotation, context);
    }

    private Object getParamFromContext(Param annotation, OperationContext context) {
        String paramName = annotation.value();
        Object arg = context.getContextParam(paramName, Object.class);

        if (arg == null && annotation.mandatory()) {
            throw new NullPointerException("Mandatory parameter " + paramName + " is empty.");
        }

        return arg;
    }
}
