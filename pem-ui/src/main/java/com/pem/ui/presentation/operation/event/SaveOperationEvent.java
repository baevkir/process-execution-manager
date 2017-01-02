package com.pem.ui.presentation.operation.event;

import com.pem.model.operation.common.OperationDTO;
import com.pem.core.rx.event.BaseEvent;

public class SaveOperationEvent extends BaseEvent {
    private OperationDTO operation;

    public SaveOperationEvent(OperationDTO operation) {
        this.operation = operation;
    }

    public OperationDTO getOperation() {
        return operation;
    }
}
