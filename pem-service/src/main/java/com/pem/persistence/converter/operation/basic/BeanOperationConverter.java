package com.pem.persistence.converter.operation.basic;

import com.pem.operation.basic.Operation;
import com.pem.persistence.converter.common.AbstractOperationConverter;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.persistence.model.operation.basic.BeanOperationEntity;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class BeanOperationConverter extends AbstractOperationConverter<BeanOperationEntity> {
    @Override
    public Operation convert(BeanOperationEntity source) {
        BeanEntity beanEntity = source.getBean();
        return getOperationProvider().createOperation(beanEntity.getBeanName(), Operation.class);
    }
}
