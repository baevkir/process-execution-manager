package com.pem.ui.presentation.operation.view.composite;

import com.pem.logic.rx.subscriber.operation.event.GetOperationListEvent;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.ui.presentation.operation.view.BaseOperationPresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class CompositeOperationPresenter extends BaseOperationPresenter<SyncCompositeOperationDTO, CompositeOperationView> {

    @Override
    protected void bindBean(SyncCompositeOperationDTO bean) {
        GetOperationListEvent event = new GetOperationListEvent();
        event.getObservable()
                .filter(operation -> bean.getId() == null || !operation.getId().equals(bean.getId()))
                .toList()
                .subscribe(operations -> {
                    getView().load(operations);
                    super.bindBean(bean);
                });
        getServiceEventBus().post(event);
    }
}
