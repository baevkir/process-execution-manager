package com.pem.logic.rx.subscriber.process.event;

import com.pem.core.rx.event.SaveEvent;
import com.pem.model.proccess.ExecutionProcessDTO;

public class UpdateProcessEvent extends SaveEvent<ExecutionProcessDTO, Void> {
    public UpdateProcessEvent(ExecutionProcessDTO source) {
        super(source);
    }
}
