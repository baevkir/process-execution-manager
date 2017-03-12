package com.pem.ui.presentation.operation.view;

import com.pem.core.rx.event.BaseEvent;
import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.rx.subscriber.operation.event.CreateOperationEvent;
import com.pem.logic.rx.subscriber.operation.event.UpdateOperationEvent;
import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.presenter.BaseBeanPresenter;
import com.pem.ui.presentation.operation.list.OperationListPresenter;
import com.pem.ui.presentation.operation.list.OperationListView;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public class BaseOperationPresenter<O extends OperationDTO, V extends BaseOperationView<O>> extends BaseBeanPresenter<O, V> {

    @Autowired
    private ServiceEventBus serviceEventBus;

    @Autowired
    private OperationListPresenter listPresenter;

    @Override
    protected void bindBean(O bean) {
        super.bindBean(bean);
        getBinderPostCommitObservable().subscribe(commitEvent -> saveOperation());
    }

    protected ServiceEventBus getServiceEventBus() {
        return serviceEventBus;
    }

    protected void navigateToOperation(BigInteger operationId) {
        UI.getCurrent().getNavigator().navigateTo(OperationListView.VIEW_NAME + "/" + operationId);
    }

    private void saveOperation() {
        serviceEventBus.post(getSaveEvent());
    }

    private BaseEvent getSaveEvent() {
        OperationDTO operation = getBean();
        if (operation.getId() == null) {
            CreateOperationEvent event = new CreateOperationEvent(operation);
            event.getSingle().subscribe(createdOperation -> {
//                navigateToOperation(createdOperation.getId());
//                listPresenter.loadAllOperations();
            });

            return event;
        }

        UpdateOperationEvent updateEvent = new UpdateOperationEvent(operation);
        updateEvent.getCompletable().subscribe(() -> {
            navigateToOperation(operation.getId());
            listPresenter.loadAllOperations();
        });
        return updateEvent;
    }
}
