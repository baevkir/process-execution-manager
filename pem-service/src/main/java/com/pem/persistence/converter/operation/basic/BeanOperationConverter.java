package com.pem.persistence.converter.operation.basic;

import com.pem.common.provider.operation.OperationProvider;
import com.pem.operation.basic.Operation;
import com.pem.persistence.converter.common.AbstractConverter;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.persistence.model.operation.basic.BeanOperationEntity;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class BeanOperationConverter extends AbstractConverter<BeanOperationEntity, Operation>{

    private OperationProvider operationProvider;

    public void setOperationProvider(OperationProvider operationProvider) {
        this.operationProvider = operationProvider;
    }

    @Override
    public Operation convert(BeanOperationEntity source) {
        BeanEntity beanEntity = source.getBean();
        return operationProvider.createOperation(beanEntity.getBeanName(), Operation.class);
    }
}
