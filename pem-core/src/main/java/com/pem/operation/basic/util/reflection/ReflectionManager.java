package com.pem.operation.basic.util.reflection;

import com.pem.context.OperationContext;

import java.lang.reflect.Method;

public interface ReflectionManager {
    Method getMethod(Class clazz);
    Object[] getArguments(Method method, OperationContext context);
    String getResultParam(Method method);
}
