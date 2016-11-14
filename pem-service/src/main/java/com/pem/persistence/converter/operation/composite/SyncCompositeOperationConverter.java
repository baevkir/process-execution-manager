package com.pem.persistence.converter.operation.composite;

import com.pem.operation.basic.Operation;
import com.pem.operation.composite.SyncCompositeOperation;
import com.pem.persistence.converter.common.AbstractOperationConverter;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.operation.composite.SyncCompositeOperationEntity;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class SyncCompositeOperationConverter extends AbstractOperationConverter<SyncCompositeOperationEntity> {

    private com.pem.persistence.converter.ConverterFactory converterFactory;

    public void setConverterFactory(com.pem.persistence.converter.ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(SyncCompositeOperationEntity source) {
        SyncCompositeOperation syncCompositeOperation = getOperationProvider().createCommonOperation(SyncCompositeOperation.class);
        syncCompositeOperation.setOperationId(String.valueOf(source.getId()));

        for (OperationEntity operationEntity: source.getOperationEntities()) {
            syncCompositeOperation.addOperation(converterFactory.convert(operationEntity, Operation.class));
        }
        return syncCompositeOperation;
    }
}
