package com.pem.persistence.mongo.converter.operation.bean;

import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.core.common.bean.BeanObject;
import com.pem.model.operation.bean.BeanOperationObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.bean.BeanOperationEntity;
import org.springframework.util.Assert;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromBeanOperationEntityToObjectConverter extends ConverterTemplateMethods implements Converter<BeanOperationEntity, BeanOperationObject> {

    @Override
    public BeanOperationObject convert(BeanOperationEntity source) {
        BeanOperationObject beanOperationDTO = new BeanOperationObject();
        fillCommonFields(beanOperationDTO, source);

        beanOperationDTO.setActive(source.isActive());
        BeanObject beanObject = source.getBean();
        Assert.notNull(beanObject);
        beanOperationDTO.setBean(beanObject);

        return beanOperationDTO;
    }
}
