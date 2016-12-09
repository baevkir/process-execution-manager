package com.pem.logic.converter.operation.basic;

import com.pem.logic.common.ServiceConstants;
import com.pem.model.operation.bean.BeanOperationDTO;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.common.bean.BeanObject;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class BeanOperationConverter extends AbstractOperationConverter<BeanOperationDTO> {
    @Override
    public Operation convert(BeanOperationDTO source) {
        BeanObject beanEntity = source.getBean();
        Operation operation = getOperationProvider().createOperation(beanEntity.getBeanName(), Operation.class);
        operation.setId(source.getId());

        return operation;
    }
}
