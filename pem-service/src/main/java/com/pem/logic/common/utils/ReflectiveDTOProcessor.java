package com.pem.logic.common.utils;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class ReflectiveDTOProcessor<S> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectiveDTOProcessor.class);
    private static final List<Class> UNPROCESSED_CLASSES = new ArrayList<>();
    private S source;
    private HandleAction action;

    static {
        UNPROCESSED_CLASSES.add(BigInteger.class);
        UNPROCESSED_CLASSES.add(BigDecimal.class);
        UNPROCESSED_CLASSES.add(String.class);
        UNPROCESSED_CLASSES.add(Date.class);
        UNPROCESSED_CLASSES.add(DateTime.class);
    }

    public S process() {
        Assert.notNull(source);
        Assert.notNull(action);

        handle(source);
        return source;
    }

    public ReflectiveDTOProcessor<S> setSource(S source) {
        this.source = source;
        return this;
    }

    public ReflectiveDTOProcessor<S> setAction(HandleAction action) {
        this.action = action;
        return this;
    }

    public interface HandleAction {
        void execute(Object object);
    }

    private void handle(Object object) {
        if (object == null) {
            return;
        }

        if (unprocessedClass(object.getClass())) {
            return;
        }

        if (object instanceof Collection) {
            handleCollection((Collection) object);
            return;
        }

        if (object instanceof Map) {
            handleMap((Map) object);
            return;
        }

        action.execute(object);

        List<PropertyDescriptor> properties = getProperties(object);
        for (PropertyDescriptor property : properties) {
            Method getMethod = property.getReadMethod();
            ReflectionUtils.makeAccessible(getMethod);
            Object value = ReflectionUtils.invokeMethod(getMethod, object);
            handle(value);
        }
    }

    private void handleCollection(Collection collection) {
        for (Object element : collection) {
            handle(element);
        }
    }

    private void handleMap(Map<Object, Object> map) {
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            handle(entry.getKey());
            handle(entry.getValue());
        }
    }

    private List<PropertyDescriptor> getProperties(Object object) {
        List<PropertyDescriptor> result = new ArrayList<>();
        Class<?> sClass = object.getClass();
        List<Field> fields = getAllFields(sClass);
        for (Field field : fields) {
            PropertyDescriptor propertyDescriptor = createPropertyDescriptor(field, sClass);
            if (propertyDescriptor == null) {
                continue;
            }
            result.add(propertyDescriptor);
        }

        return result;
    }

    private PropertyDescriptor createPropertyDescriptor(Field field, Class clazz) {
        try {
            return new PropertyDescriptor(field.getName(), clazz);
        } catch (IntrospectionException exception) {
            LOGGER.error("Can't find read method for Field " + field, exception);
            return null;
        }
    }

    private List<Field> getAllFields(Class sourceClass) {
        Assert.notNull(sourceClass, "sourceClass must not be NULL.");
        List<Field> result = new ArrayList<>();

        Class<?> clazz = sourceClass;
        while (!Object.class.equals(clazz) && clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                result.add(field);
            }
            clazz = clazz.getSuperclass();
        }

        return result;
    }

    private boolean unprocessedClass(Class clazz){
        if (Object.class.equals(clazz)) {
            return true;
        }
        if (ClassUtils.isPrimitiveOrWrapper(clazz)) {
            return true;
        }
        if (UNPROCESSED_CLASSES.contains(clazz)) {
            return true;
        }

        return false;
    }
}

