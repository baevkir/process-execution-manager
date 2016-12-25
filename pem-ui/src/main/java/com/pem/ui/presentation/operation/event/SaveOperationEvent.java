package com.pem.ui.presentation.operation.event;

import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.event.AbstracEvent;

public class SaveOperationEvent extends AbstracEvent {
    private OperationDTO operation;

    public SaveOperationEvent(OperationDTO operation) {
        this.operation = operation;
    }

    public OperationDTO getOperation() {
        return operation;
    }
}
