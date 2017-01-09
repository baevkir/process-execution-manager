package com.pem.logic.rx.subscriber.operation.event;

import com.pem.core.rx.event.CreateEvent;
import com.pem.model.operation.common.OperationDTO;

public class CreateOperationEvent extends CreateEvent<OperationDTO, OperationDTO> {

    public CreateOperationEvent(OperationDTO source) {
        super(source);
    }
}
