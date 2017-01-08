package com.pem.logic.rx.subscriber.process.event;

import com.pem.core.rx.event.ObservableEvent;
import com.pem.model.proccess.ExecutionProcessDTO;

public class ExecuteProcessEvent extends ObservableEvent<ExecutionProcessDTO, Void> {
    public ExecuteProcessEvent(ExecutionProcessDTO source) {
        super(source);
    }
}
