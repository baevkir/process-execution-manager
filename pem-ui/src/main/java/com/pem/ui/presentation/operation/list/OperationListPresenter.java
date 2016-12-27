package com.pem.ui.presentation.operation.list;

import com.google.common.eventbus.Subscribe;
import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.presenter.AbstractPresenter;
import com.pem.ui.presentation.common.view.BindForm;
import com.pem.ui.presentation.common.view.provider.PemViewProvider;
import com.pem.ui.presentation.operation.event.ChooseNewOperationTypeEvent;
import com.pem.ui.presentation.operation.event.OpenOperationEvent;
import com.pem.ui.presentation.operation.event.SaveOperationEvent;
import com.pem.ui.presentation.operation.event.ShowOperationsListEvent;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;
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

    @Autowired
    private ChooseOperationTypeWindow chooseOperationTypeWindow;

    @Subscribe
    public void loadAllOeartions(ShowOperationsListEvent event) {
        event.getOperationsLoader().load(operationService.getAllOperations());
    }

    @Subscribe
    public void openOperationForm(OpenOperationEvent event) {
        BigInteger operationId = event.getOperationId();
        OperationDTO operation;
        if (operationId != null) {
            operation = operationService.getOperation(operationId);
        }else {
            Class<? extends OperationDTO> operationType = event.getOperationType();
            Assert.notNull(operationType);
            try {
                operation = operationType.newInstance();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }

        View operationView = viewProvider.getView(operation.getClass());

        Assert.notNull(operationView);
        Assert.isInstanceOf(BindForm.class, operationView);

        BindForm operationForm = (BindForm) operationView;
        operationForm.bind(operation);

        getView().openOperation(operationForm);
    }

    @Subscribe
    public void saveOperation(SaveOperationEvent event) {
        OperationDTO operation = event.getOperation();

        BigInteger operationId = operation.getId();
        if (operationId == null) {
            operationId = operationService.createOperation(operation).getId();
        } else {
            operationService.updateOperation(operation);
        }

        getEventBus().post(new ShowOperationsListEvent(getView()));
        UI.getCurrent().getNavigator().navigateTo(OperationListView.VIEW_NAME + "/" + operationId);
    }

    @Subscribe
    public void chooseOperationType(ChooseNewOperationTypeEvent event) {
        UI.getCurrent().addWindow(chooseOperationTypeWindow);
    }
}
