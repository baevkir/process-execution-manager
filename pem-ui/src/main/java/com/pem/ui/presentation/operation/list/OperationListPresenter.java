package com.pem.ui.presentation.operation.list;

import com.google.common.eventbus.Subscribe;
import com.pem.logic.service.operation.OperationService;
import com.pem.ui.presentation.common.presenter.AbstractPresenter;
import com.pem.ui.presentation.operation.event.OpenOperationEvent;
import com.pem.ui.presentation.operation.event.ShowOperationsListEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class OperationListPresenter extends AbstractPresenter<OperationListView> {

    @Autowired
    private OperationService operationService;

    @Subscribe
    public void loadAllOeartions(ShowOperationsListEvent event) {
        getView().loadOperations(operationService.getAllOperations());
    }

    @Subscribe
    public void openOperation(OpenOperationEvent event) {
        getView().openOperation(new Label(String.valueOf(event.getOperationId())));
    }
}
