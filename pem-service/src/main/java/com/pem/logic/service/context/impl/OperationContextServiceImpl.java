package com.pem.logic.service.context.impl;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.context.OperationContext;
import com.pem.logic.bean.provider.BeanProvider;
import com.pem.logic.common.context.OperationContextFactory;
import com.pem.logic.service.context.OperationContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class OperationContextServiceImpl implements OperationContextService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationContextServiceImpl.class);
    private BeanProvider beanProvider;

    @Override
    public OperationContextFactory getContextFactory() {
        return OperationContextFactory.create(beanProvider);
    }

    @Override
    public Flux<BeanObject> getAvailableContextes() {
        return Flux.fromIterable(beanProvider.getAllForType(OperationContext.class));
    }

    public void setBeanProvider(BeanProvider beanProvider) {
        this.beanProvider = beanProvider;
    }
}
