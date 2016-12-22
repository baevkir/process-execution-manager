package com.pem.ui.presentation.operation.event;

import com.pem.ui.presentation.common.event.AbstracEvent;

import java.math.BigInteger;

public class OpenOperationEvent extends AbstracEvent {

    private BigInteger operationId;

    public OpenOperationEvent(BigInteger operationId) {
        this.operationId = operationId;
    }

    public BigInteger getOperationId() {
        return operationId;
    }
}
