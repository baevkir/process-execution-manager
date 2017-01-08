package com.pem.logic.rx.subscriber.process.event;

import com.pem.core.rx.event.SaveEvent;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;

public class CreateProcessEvent extends SaveEvent<OperationDTO, ExecutionProcessDTO> {

    public CreateProcessEvent(OperationDTO source) {
        super(source);
    }
}
