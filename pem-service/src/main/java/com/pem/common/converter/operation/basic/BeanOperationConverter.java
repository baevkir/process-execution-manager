package com.pem.common.converter.operation.basic;

import com.pem.operation.basic.Operation;
import com.pem.common.converter.common.AbstractOperationConverter;
import com.pem.common.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.persistence.model.operation.basic.BeanOperationEntity;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class BeanOperationConverter extends AbstractOperationConverter<BeanOperationEntity> {
    @Override
    public Operation convert(BeanOperationEntity source) {
        BeanEntity beanEntity = source.getBean();
        Operation operation = getOperationProvider().createOperation(beanEntity.getBeanName(), Operation.class);
        operation.setOperationId(String.valueOf(source.getId()));

        return operation;
    }
}
