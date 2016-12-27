package com.pem.ui.presentation.operation.event;


import com.pem.ui.presentation.common.event.AbstracEvent;
import com.pem.ui.presentation.operation.list.OperationsLoader;

public class ShowOperationsListEvent extends AbstracEvent {
    private OperationsLoader operationsLoader;

    public ShowOperationsListEvent(OperationsLoader operationsLoader) {
        this.operationsLoader = operationsLoader;
    }

    public OperationsLoader getOperationsLoader() {
        return operationsLoader;
    }
}
