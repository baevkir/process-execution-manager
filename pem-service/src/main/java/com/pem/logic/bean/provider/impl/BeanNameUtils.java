package com.pem.logic.bean.provider.impl;

import com.pem.core.operation.basic.Operation;
import com.pem.logic.common.ServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanNameUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanNameUtils.class);
    private static final String OPERATION_BEAN_PREF = ServiceConstants.CUSTOM_OPERATION_BEAN_PREF;

    public static  <O extends Operation> String getCommonOperationName(Class<O> operationClass) {
        String className = operationClass.getSimpleName();
        LOGGER.trace("Get class name {}.", className);
        return OPERATION_BEAN_PREF + className;
    }
}
