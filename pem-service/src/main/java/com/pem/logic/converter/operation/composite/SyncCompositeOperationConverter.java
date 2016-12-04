package com.pem.logic.converter.operation.composite;

import com.pem.core.converter.factory.ConverterFactory;
import com.pem.core.operation.composite.SyncCompositeOperation;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.core.converter.impl.RegisterInConverterFactory;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class SyncCompositeOperationConverter extends AbstractOperationConverter<SyncCompositeOperationDTO> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(SyncCompositeOperationDTO source) {
        SyncCompositeOperation syncCompositeOperation = getOperationProvider().createCommonOperation(SyncCompositeOperation.class);
        syncCompositeOperation.setId(source.getId());

        for (OperationDTO operationEntity: source.getOperations()) {
            syncCompositeOperation.addOperation(converterFactory.convert(operationEntity, Operation.class));
        }
        return syncCompositeOperation;
    }
}
