package com.pem.common.provider.context.impl;

import com.pem.common.provider.context.OperationContextProvider;
import com.pem.common.utils.ApplicationContextWrapper;
import com.pem.context.OperationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class OperationContextProviderImpl implements OperationContextProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationContextProviderImpl.class);
    private static final String CONTEXT_BEAN_NAME = "operationContext";

    @Autowired
    private ApplicationContext context;

    @Override
    public OperationContext createContext() {
        ApplicationContextWrapper wrapper = new ApplicationContextWrapper(context);
        return wrapper.getPrototypeBeanByType(CONTEXT_BEAN_NAME, OperationContext.class);
    }
}
