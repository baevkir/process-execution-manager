package com.pem.logic.rx.subscriber.operation.event;

import com.pem.core.rx.event.SaveEvent;
import com.pem.model.operation.common.OperationDTO;

public class CreateOperationEvent extends SaveEvent<OperationDTO> {

    public CreateOperationEvent(OperationDTO source) {
        super(source);
    }
}
