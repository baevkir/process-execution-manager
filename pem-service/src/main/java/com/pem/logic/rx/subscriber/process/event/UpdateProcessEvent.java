package com.pem.logic.rx.subscriber.process.event;

import com.pem.core.rx.event.UpdateEvent;
import com.pem.model.proccess.ExecutionProcessDTO;

public class UpdateProcessEvent extends UpdateEvent<ExecutionProcessDTO> {
    public UpdateProcessEvent(ExecutionProcessDTO source) {
        super(source);
    }
}
