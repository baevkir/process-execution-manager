package com.pem.persistence.mongo.converter.operation.bean;

import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.core.common.applicationcontext.bean.BeanObject;
import com.pem.model.operation.bean.BeanOperationObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.bean.BeanOperationEntity;
import org.springframework.util.Assert;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromBeanOperationObjectToEntityConverter extends ConverterTemplateMethods implements Converter<BeanOperationObject, BeanOperationEntity> {

    @Override
    public BeanOperationEntity convert(BeanOperationObject source) {
        BeanOperationEntity beanOperationEntity = new BeanOperationEntity();
        fillCommonFields(beanOperationEntity, source);

        beanOperationEntity.setActive(source.isActive());
        BeanObject beanObject = source.getBean();
        Assert.notNull(beanObject);
        beanOperationEntity.setBean(beanObject);

        return beanOperationEntity;
    }
}
