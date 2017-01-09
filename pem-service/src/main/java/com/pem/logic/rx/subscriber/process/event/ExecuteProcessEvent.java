package com.pem.logic.rx.subscriber.process.event;

import com.pem.core.rx.event.CompletableEvent;
import com.pem.model.proccess.ExecutionProcessDTO;

public class ExecuteProcessEvent extends CompletableEvent {
    private ExecutionProcessDTO process;

    public ExecuteProcessEvent(ExecutionProcessDTO process) {
        this.process = process;
    }

    public ExecutionProcessDTO getProcess() {
        return process;
    }
}
