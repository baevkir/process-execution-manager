package com.pem.logic.rx.subscriber.operation.event;

import com.pem.core.rx.event.UpdateEvent;
import com.pem.model.operation.common.OperationDTO;

public class UpdateOperationEvent extends UpdateEvent<OperationDTO> {
    public UpdateOperationEvent(OperationDTO source) {
        super(source);
    }
}
