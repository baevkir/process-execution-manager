package com.pem.logic.converter.operation.composite;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.operation.composite.SyncCompositeOperation;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.converter.operation.common.AbstractOperationConverter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;

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

        for (OperationObject operationEntity: source.getOperations()) {
            syncCompositeOperation.addOperation(converterFactory.convert(operationEntity, Operation.class));
        }
        return syncCompositeOperation;
    }
}
