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
        SyncCompositeOperationDTO syncCompositeOperationDTO = new SyncCompositeOperationDTO();
        fillCommonFields(syncCompositeOperationDTO, source);

        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (OperationEntity operationEntity : source.getOperationEntities()) {
            OperationDTO operationDTO = converterFactory.convert(operationEntity, OperationDTO.class);
            checkActive(syncCompositeOperationDTO, operationDTO);
            operationDTOs.add(operationDTO);
        }

        syncCompositeOperationDTO.setOperations(operationDTOs);

        return syncCompositeOperationDTO;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
