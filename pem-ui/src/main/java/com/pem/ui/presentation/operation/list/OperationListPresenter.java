package com.pem.ui.presentation.operation.list;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.pem.logic.service.operation.OperationService;
import com.pem.ui.presentation.operation.event.ShowOperationsListEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringComponent
@UIScope
public class OperationListPresenter {

    @Autowired
    private OperationService operationService;

    @Autowired
    private EventBus eventBus;

    private OperationListView operationListView;

    @Subscribe
    public void loadAllOeartions(ShowOperationsListEvent event) {
        operationListView.loadOperations(operationService.getAllOperations());
    }

    public void bind(OperationListView operationListView) {
        this.operationListView = operationListView;
    }

    @PostConstruct
    public void init() {
        eventBus.register(this);
    }

    @PreDestroy
    public void destroy() {
        eventBus.unregister(this);
    }
}
