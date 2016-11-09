package com.pem.operation.basic.util.reflection;

import com.google.common.collect.Iterables;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Parameter;
import com.pem.context.OperationContext;
import com.pem.operation.basic.util.reflection.annotions.OperationMethod;
import com.pem.operation.basic.util.reflection.annotions.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReflectionManagerImpl implements ReflectionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionManagerImpl.class);

    @Override
    public Method getMethod(Class clazz) {
        LOGGER.trace("Start to find OperationMethod in class {}.", clazz);
        List<Method> methods = Arrays.asList(clazz.getDeclaredMethods());

        List<Method> operationMethods = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(OperationMethod.class)) {
                LOGGER.debug("Find OperationMethod {} for class {}.", method, clazz);
                operationMethods.add(method);
            }
        }

        Assert.notEmpty(operationMethods, String.format("Can't execute %s. There is no class annotated @OperationMethod.", clazz));
        Assert.isTrue(operationMethods.size() == 1, String.format("Can't execute %s. Annotation  @OperationMethod used more than one time.", clazz));

        return Iterables.getFirst(operationMethods, null);
    }

    @Override
    public Object[] getArguments(Method method, OperationContext context) {
        List<Object> args = new LinkedList<>();
        Invokable invokable = Invokable.from(method);
        List<Parameter> parameters = invokable.getParameters();
        for (Parameter parameter : parameters) {
            args.add(getArgFromContext(context, parameter));
        }
        return args.toArray();
    }

    @Override
    public String getResultParam(Method method) {
        OperationMethod annotation = method.getAnnotation(OperationMethod.class);
        return annotation.result();
    }

    private Object getArgFromContext(OperationContext context, Parameter parameter) {
        if (!parameter.isAnnotationPresent(Param.class)) {
            throw new NullPointerException("Parameter " + parameter + " does not market with annotation @Param.");
        }

        Param annotation = parameter.getAnnotation(Param.class);
        String paramName = annotation.value();
        Object arg = context.getContextParam(paramName, Object.class);

        if (arg == null && annotation.mandatory()) {
            throw new NullPointerException("Mandatory parameter " + parameter + " is empty.");
        }

        return arg;
    }
}
