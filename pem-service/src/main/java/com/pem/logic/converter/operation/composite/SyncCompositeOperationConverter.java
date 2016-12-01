package com.pem.logic.converter.operation.composite;

import com.pem.operation.basic.Operation;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.logic.converter.common.RegisterInConverterFactory;
import com.pem.persistence.api.model.operation.common.OperationObject;
import com.pem.persistence.api.model.operation.composite.SyncCompositeOperationObject;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class SyncCompositeOperationConverter extends AbstractOperationConverter<SyncCompositeOperationObject> {

    private com.pem.logic.converter.ConverterFactory converterFactory;

    public void setConverterFactory(com.pem.logic.converter.ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(SyncCompositeOperationObject source) {
        com.pem.operation.composite.SyncCompositeOperation syncCompositeOperation = getOperationProvider().createCommonOperation(com.pem.operation.composite.SyncCompositeOperation.class);
        syncCompositeOperation.setOperationId(String.valueOf(source.getId()));

        for (OperationObject operationEntity: source.getOperationEntities()) {
            syncCompositeOperation.addOperation(converterFactory.convert(operationEntity, Operation.class));
        }
        return syncCompositeOperation;
    }
}
