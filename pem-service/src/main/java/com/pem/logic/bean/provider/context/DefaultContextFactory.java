package com.pem.logic.bean.provider.context;

import com.pem.core.context.OperationContext;
import com.pem.core.context.OperationContextImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class DefaultContextFactory extends AbstractFactoryBean<OperationContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultContextFactory.class);

    @Override
    public Class<?> getObjectType() {
        return OperationContext.class;
    }

    @Override
    protected OperationContext createInstance() throws Exception {
        LOGGER.trace("Create OperationContext");
        return new OperationContextImpl();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
