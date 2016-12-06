package com.pem.persistence.mongo.converter.operation.bean;

import com.pem.core.converter.impl.Converter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.common.bean.BeanObject;
import com.pem.model.operation.basic.BeanOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.bean.BeanOperationEntity;
import org.springframework.util.Assert;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromBeanOperationDTOToEntityConverter extends ConverterTemplateMethods implements Converter<BeanOperationDTO, BeanOperationEntity> {

    @Override
    public BeanOperationEntity convert(BeanOperationDTO source) {
        BeanOperationEntity beanOperationEntity = new BeanOperationEntity();
        fillCommonFields(beanOperationEntity, source);

        beanOperationEntity.setActive(source.isActive());
        BeanObject beanObject = source.getBean();
        Assert.notNull(beanObject);
        beanOperationEntity.setBean(beanObject);

        return beanOperationEntity;
    }
}
