package com.pem.persistence.converter.operation.composite;

import com.pem.common.provider.operation.OperationProvider;
import com.pem.operation.basic.Operation;
import com.pem.operation.composite.SyncCompositeOperation;
import com.pem.persistence.converter.ConverterFactory;
import com.pem.persistence.converter.common.AbstractConverter;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.operation.composite.SyncCompositeOperationEntity;

public class SyncCompositeOperationConverter extends AbstractConverter<SyncCompositeOperationEntity, Operation> {

    private OperationProvider operationProvider;

    private ConverterFactory converterFactory;

    public void setOperationProvider(OperationProvider operationProvider) {
        this.operationProvider = operationProvider;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(SyncCompositeOperationEntity source) {
        SyncCompositeOperation syncCompositeOperation = operationProvider.createCommonOperation(SyncCompositeOperation.class);

        for (OperationEntity operationEntity: source.getOperationEntities()) {
            syncCompositeOperation.addOperation(converterFactory.convert(operationEntity, Operation.class));
        }
        return syncCompositeOperation;
    }
}
