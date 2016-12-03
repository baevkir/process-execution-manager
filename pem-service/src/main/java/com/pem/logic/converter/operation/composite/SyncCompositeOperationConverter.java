package com.pem.logic.converter.operation.composite;

import com.pem.core.operation.composite.SyncCompositeOperation;
import com.pem.logic.common.Constants;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.logic.converter.common.RegisterInConverterFactory;

@RegisterInConverterFactory(factories = Constants.CONVERTER_FACTORY_NAME)
public class SyncCompositeOperationConverter extends AbstractOperationConverter<SyncCompositeOperationDTO> {

    private com.pem.logic.converter.ConverterFactory converterFactory;

    public void setConverterFactory(com.pem.logic.converter.ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(SyncCompositeOperationDTO source) {
        SyncCompositeOperation syncCompositeOperation = getOperationProvider().createCommonOperation(SyncCompositeOperation.class);
        syncCompositeOperation.setOperationId(String.valueOf(source.getId()));

        for (OperationDTO operationEntity: source.getOperationEntities()) {
            syncCompositeOperation.addOperation(converterFactory.convert(operationEntity, Operation.class));
        }
        return syncCompositeOperation;
    }
}
