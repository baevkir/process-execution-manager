package com.pem.ui.presentation.operation.event;


import com.pem.core.rx.event.BaseEvent;
import com.pem.ui.presentation.operation.list.OperationsLoader;

public class ShowOperationsListEvent extends BaseEvent {
    private OperationsLoader operationsLoader;

    public ShowOperationsListEvent(OperationsLoader operationsLoader) {
        this.operationsLoader = operationsLoader;
    }

    public OperationsLoader getOperationsLoader() {
        return operationsLoader;
    }
}
