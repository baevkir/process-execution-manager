package com.pem.logic.converter.operation.basic;

import com.pem.model.operation.basic.BeanOperationDTO;
import com.pem.operation.basic.Operation;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.logic.converter.common.RegisterInConverterFactory;
import com.pem.model.common.bean.BeanObject;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class BeanOperationConverter extends AbstractOperationConverter<BeanOperationDTO> {
    @Override
    public Operation convert(BeanOperationDTO source) {
        BeanObject beanEntity = source.getBean();
        Operation operation = getOperationProvider().createOperation(beanEntity.getBeanName(), Operation.class);
        operation.setOperationId(String.valueOf(source.getId()));

        return operation;
    }
}
