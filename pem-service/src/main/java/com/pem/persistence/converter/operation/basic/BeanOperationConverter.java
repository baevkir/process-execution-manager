package com.pem.persistence.converter.operation.basic;

import com.pem.common.provider.OperationProvider;
import com.pem.operation.basic.Operation;
import com.pem.persistence.converter.common.AbstractConverter;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.persistence.model.operation.basic.BeanOperationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RegisterInConverterFactory(factoryName = "converterFactory")
@Component
public class BeanOperationConverter extends AbstractConverter<BeanOperationEntity, Operation>{

    @Autowired
    private OperationProvider provider;
    @Override
    public Operation convert(BeanOperationEntity source) {
        BeanEntity beanEntity = source.getBean();
        return provider.getOperation(beanEntity.getBeanName());
    }
}
