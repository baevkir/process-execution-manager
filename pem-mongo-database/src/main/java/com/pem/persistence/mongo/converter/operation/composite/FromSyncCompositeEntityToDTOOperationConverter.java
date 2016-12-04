package com.pem.persistence.mongo.converter.operation.composite;

import com.pem.core.converter.factory.ConverterFactory;
import com.pem.core.converter.impl.Converter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.composite.SyncCompositeOperationEntity;

import java.util.ArrayList;
import java.util.List;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromSyncCompositeEntityToDTOOperationConverter extends ConverterTemplateMethods implements Converter<SyncCompositeOperationEntity, SyncCompositeOperationDTO> {

    private ConverterFactory converterFactory;

    @Override
    public SyncCompositeOperationDTO convert(SyncCompositeOperationEntity source) {
        SyncCompositeOperationDTO operationDTO = new SyncCompositeOperationDTO();
        fillCommonFields(operationDTO, source);

        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (OperationEntity operationEntity : source.getOperationEntities()) {
            operationDTOs.add(converterFactory.convert(operationEntity, OperationDTO.class));
        }

        operationDTO.setOperations(operationDTOs);

        return operationDTO;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
