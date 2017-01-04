package com.pem.logic.rx.subscriber.operation.event;

import com.pem.core.rx.event.SaveEvent;
import com.pem.model.operation.common.OperationDTO;

public class UpdateOperationEvent extends SaveEvent<OperationDTO> {

    public UpdateOperationEvent(OperationDTO source) {
        super(source);
    }
}
