package com.pem.persistence.mongo.converter.operation.composite;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.composite.SyncCompositeOperationEntity;

import java.util.ArrayList;
import java.util.List;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromSyncCompositeObjectToEntityOperationConverter extends ConverterTemplateMethods implements Converter<SyncCompositeOperationDTO, SyncCompositeOperationEntity> {

    private ConverterFactory converterFactory;

    @Override
    public SyncCompositeOperationEntity convert(SyncCompositeOperationDTO source) {
        SyncCompositeOperationEntity operationEntity = new SyncCompositeOperationEntity();
        fillCommonFields(operationEntity, source);

        List<OperationEntity> operationEntities = new ArrayList<>();
        for (OperationObject operationDTO : source.getOperations()) {
            operationEntities.add(converterFactory.convert(operationDTO, OperationEntity.class));
        }

        operationEntity.setOperationEntities(operationEntities);

        return operationEntity;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
