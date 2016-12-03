package com.pem.logic.bean.provider.context.impl;

import com.pem.logic.bean.provider.context.OperationContextProvider;
import com.pem.core.common.ApplicationContextWrapper;
import com.pem.core.context.OperationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class OperationContextProviderImpl implements OperationContextProvider, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationContextProviderImpl.class);
    private static final String CONTEXT_BEAN_NAME = "operationContext";

    private ApplicationContext applicationContext;

    @Override
    public OperationContext createContext() {
        ApplicationContextWrapper wrapper = new ApplicationContextWrapper(applicationContext);
        return wrapper.getPrototypeBeanByType(CONTEXT_BEAN_NAME, OperationContext.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
