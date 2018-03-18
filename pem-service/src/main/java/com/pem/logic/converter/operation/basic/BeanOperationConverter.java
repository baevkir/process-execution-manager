package com.pem.logic.converter.operation.basic;

import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.common.ServiceConstants;
import com.pem.logic.converter.operation.common.AbstractOperationConverter;
import com.pem.model.operation.bean.BeanOperationObject;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class BeanOperationConverter extends AbstractOperationConverter<BeanOperationObject> {
    @Override
    public Operation convert(BeanOperationObject source) {
        Operation operation = getBeanProvider().createInstance(source.getBean(), Operation.class);
        operation.setId(source.getId());

        return operation;
    }
}
