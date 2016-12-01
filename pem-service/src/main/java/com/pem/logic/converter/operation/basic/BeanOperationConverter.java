package com.pem.logic.converter.operation.basic;

import com.pem.operation.basic.Operation;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.logic.converter.common.RegisterInConverterFactory;
import com.pem.persistence.api.model.common.bean.BeanObject;
import com.pem.persistence.api.model.operation.basic.BeanOperationObject;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class BeanOperationConverter extends AbstractOperationConverter<BeanOperationObject> {
    @Override
    public Operation convert(BeanOperationObject source) {
        BeanObject beanEntity = source.getBean();
        Operation operation = getOperationProvider().createOperation(beanEntity.getBeanName(), Operation.class);
        operation.setOperationId(String.valueOf(source.getId()));

        return operation;
    }
}
