package com.pem.persistence.mongo.converter.operation.composite;

import com.pem.core.common.converter.ConverterFactory;
import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.composite.SyncCompositeOperationEntity;

import java.util.ArrayList;
import java.util.List;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromSyncCompositeEntityToObjectOperationConverter extends ConverterTemplateMethods implements Converter<SyncCompositeOperationEntity, SyncCompositeOperationDTO> {

    private ConverterFactory converterFactory;

    @Override
    public SyncCompositeOperationDTO convert(SyncCompositeOperationEntity source) {
        SyncCompositeOperationDTO syncCompositeOperationDTO = new SyncCompositeOperationDTO();
        fillCommonFields(syncCompositeOperationDTO, source);

        List<OperationObject> operationDTOs = new ArrayList<>();
        for (OperationEntity operationEntity : source.getOperationEntities()) {
            OperationObject operationDTO = converterFactory.convert(operationEntity, OperationObject.class);
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
