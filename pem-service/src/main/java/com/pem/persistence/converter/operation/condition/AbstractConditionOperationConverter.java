package com.pem.persistence.converter.operation.condition;

import com.pem.persistence.converter.ConverterFactory;
import com.pem.persistence.converter.common.AbstractOperationConverter;
import com.pem.persistence.model.operation.condition.ConditionOperationEntity;

public abstract class AbstractConditionOperationConverter<S extends ConditionOperationEntity> extends AbstractOperationConverter<S> {

    private com.pem.persistence.converter.ConverterFactory converterFactory;

    public void setConverterFactory(com.pem.persistence.converter.ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    protected ConverterFactory getConverterFactory() {
        return converterFactory;
    }
}
