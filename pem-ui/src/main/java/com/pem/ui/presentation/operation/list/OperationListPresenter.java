package com.pem.ui.presentation.operation.list;

import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.rx.subscriber.operation.event.GetOperationEvent;
import com.pem.logic.rx.subscriber.operation.event.GetOperationListEvent;
import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.presenter.BasePresenter;
import com.pem.ui.presentation.common.view.provider.PemViewProvider;
import com.pem.ui.presentation.operation.view.BaseOperationView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.math.BigInteger;

@SpringComponent
@UIScope
public class OperationListPresenter extends BasePresenter<OperationListView> {

    @Autowired
    private ServiceEventBus serviceEventBus;

    @Autowired
    private PemViewProvider viewProvider;

    @Autowired
    private ChooseOperationTypeWindow chooseOperationTypeWindow;

    @Override
    protected void initViewHandlers() {
        OperationList operationList = getView().getOperationList();
        getView().getViewObservable()
                .filter(event -> !operationList.isDataLoaded())
                .subscribe(event -> loadAllOperations());

        getView().getViewObservable()
                .map(event -> event.getParameters())
                .filter(parameters -> StringUtils.isNotEmpty(parameters) && StringUtils.isNumeric(parameters))
                .map(parameters -> new BigInteger(parameters))
                .subscribe(operationId -> openOperationForm(operationId));

        operationList.getSelectObservable()
                .filter(event -> event.getItem() != null)
                .map(event -> (OperationDTO) event.getItemId())
                .map(operation -> operation.getId())
                .subscribe(operationId -> navigateToOperation(operationId));

        operationList.getCreateButtonObservable()
                .subscribe(clickEvent -> openChooseOperationTypeWindow());

        chooseOperationTypeWindow.getOkButtonObservable()
                .filter(clickEvent -> chooseOperationTypeWindow.getValue() != null)
                .map(clickEvent -> chooseOperationTypeWindow.getValue().getOperationType())
                .subscribe(operationType -> {
                    openOperationForm(operationType);
                    chooseOperationTypeWindow.close();
                });

    }

    public void loadAllOperations() {
        OperationList operationList = getView().getOperationList();
        GetOperationListEvent event = new GetOperationListEvent();
        event.getObservable().toList().subscribe(operations -> operationList.load(operations));
        serviceEventBus.post(event);
    }

    private void openOperationForm(BigInteger operationId) {
        GetOperationEvent event = new GetOperationEvent(operationId);
        event.getSingle().subscribe(operation -> openOperationForm(operation));
        serviceEventBus.post(event);
    }

    private void openOperationForm(Class<? extends OperationDTO> operationType) {
        Assert.notNull(operationType);
        try {
            OperationDTO operation = operationType.newInstance();
            openOperationForm(operation);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void openOperationForm(OperationDTO operation) {
        View operationView = viewProvider.getView(operation.getClass());

        Assert.notNull(operationView);
        Assert.isInstanceOf(BaseOperationView.class, operationView);

        BaseOperationView operationForm = (BaseOperationView) operationView;
        operationForm.bind(operation);

        getView().openOperation(operationForm);
    }

    private void openChooseOperationTypeWindow() {
        UI.getCurrent().addWindow(chooseOperationTypeWindow);
    }

    private void navigateToOperation(BigInteger operationId) {
        UI.getCurrent().getNavigator().navigateTo(OperationListView.VIEW_NAME + "/" + operationId);
    }
}