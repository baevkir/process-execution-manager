package com.pem.logic.converter.operation.common;

import com.pem.core.common.converter.impl.Converter;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.bean.provider.BeanProvider;

public abstract class AbstractOperationConverter<S> implements Converter<S, Operation> {

    private BeanProvider beanProvider;

    public void setBeanProvider(BeanProvider beanProvider) {
        this.beanProvider = beanProvider;
    }

    protected BeanProvider getBeanProvider() {
        return beanProvider;
    }
}
