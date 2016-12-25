package com.pem.ui.presentation.operation.list;

import com.google.common.eventbus.Subscribe;
import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.presenter.AbstractPresenter;
import com.pem.ui.presentation.common.view.BindForm;
import com.pem.ui.presentation.common.view.provider.PemViewProvider;
import com.pem.ui.presentation.operation.event.OpenOperationEvent;
import com.pem.ui.presentation.operation.event.SaveOperationEvent;
import com.pem.ui.presentation.operation.event.ShowOperationsListEvent;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.math.BigInteger;

@SpringComponent
@UIScope
public class OperationListPresenter extends AbstractPresenter<OperationListView> {

    @Autowired
    private OperationService operationService;

    @Autowired
    private PemViewProvider viewProvider;

    @Subscribe
    public void loadAllOeartions(ShowOperationsListEvent event) {
        getView().loadOperations(operationService.getAllOperations());
    }

    @Subscribe
    public void openOperationForm(OpenOperationEvent event) {
        BigInteger operationId = event.getOperationId();
        Assert.notNull(operationId, "operationId is NULL. Can't open operation form");
        OperationDTO operation = operationService.getOperation(operationId);

        View operationView = viewProvider.getView(operation);

        Assert.notNull(operationView);
        Assert.isInstanceOf(BindForm.class, operationView);

        BindForm operationForm = (BindForm) operationView;
        operationForm.bind(operation);

        getView().openOperation(operationForm);
    }

    @Subscribe
    public void saveOperation(SaveOperationEvent event) {
        OperationDTO operation = event.getOperation();

        if (operation.getId() == null) {
            operationService.createOperation(operation);
        } else {
            operationService.updateOperation(operation);
        }

        getEventBus().post(new ShowOperationsListEvent());
    }
}
