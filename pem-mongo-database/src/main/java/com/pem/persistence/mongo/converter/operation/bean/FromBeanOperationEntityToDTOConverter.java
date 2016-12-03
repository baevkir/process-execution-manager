package com.pem.persistence.mongo.converter.operation.bean;

import com.pem.core.converter.impl.Converter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.common.bean.BeanObject;
import com.pem.model.operation.basic.BeanOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.basic.BeanOperationEntity;
import org.springframework.util.Assert;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromBeanOperationEntityToDTOConverter extends ConverterTemplateMethods implements Converter<BeanOperationEntity, BeanOperationDTO> {

    @Override
    public BeanOperationDTO convert(BeanOperationEntity source) {
        BeanOperationDTO beanOperationDTO = new BeanOperationDTO();
        fillCommonFields(beanOperationDTO, source);

        BeanObject beanObject = source.getBean();
        Assert.notNull(beanObject);
        beanOperationDTO.setBean(beanObject);

        return beanOperationDTO;
    }
}
