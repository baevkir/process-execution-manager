package com.pem.ui.presentation.operation.view;

import com.pem.core.rx.event.SaveEvent;
import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.rx.subscriber.operation.event.CreateOperationEvent;
import com.pem.logic.rx.subscriber.operation.event.UpdateOperationEvent;
import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.presenter.BaseBeanPresenter;
import com.pem.ui.presentation.operation.list.OperationListView;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public class BaseOperationPresenter<O extends OperationDTO, V extends BaseOperationView<O>> extends BaseBeanPresenter<O, V> {

    @Autowired
    private ServiceEventBus serviceEventBus;

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
        SaveEvent<OperationDTO> saveEvent = getSaveEvent();
        serviceEventBus.post(saveEvent.setNotificationObserver(notification -> {
            if (notification.hasThrowable()) {
                throw new RuntimeException(notification.getThrowable());
            }
            if (notification.isOnNext()) {
                navigateToOperation(notification.getValue().getId());
            }
        }));
    }

    private SaveEvent<OperationDTO> getSaveEvent() {
        OperationDTO operation = getBean();
        if (operation.getId() == null) {
            return new CreateOperationEvent(operation);
        }

        return new UpdateOperationEvent(operation);
    }
}
