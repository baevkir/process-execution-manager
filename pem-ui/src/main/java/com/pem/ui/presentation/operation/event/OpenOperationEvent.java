package com.pem.ui.presentation.operation.event;

import com.pem.model.operation.common.OperationDTO;
import com.pem.core.rx.event.BaseEvent;

import java.math.BigInteger;

public class OpenOperationEvent extends BaseEvent {

    private BigInteger operationId;

    private Class<? extends OperationDTO> operationType;

    public OpenOperationEvent(BigInteger operationId) {
        this.operationId = operationId;
    }

    public OpenOperationEvent(Class<? extends OperationDTO> operationType) {
        this.operationType = operationType;
    }

    public BigInteger getOperationId() {
        return operationId;
    }

    public Class<? extends OperationDTO> getOperationType() {
        return operationType;
    }
}
