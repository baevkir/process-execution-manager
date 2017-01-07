package com.pem.ui.presentation.operation.view;

import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.presenter.BaseBeanPresenter;
import com.pem.ui.presentation.operation.event.SaveOperationEvent;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseOperationPresenter<O extends OperationDTO, V extends BaseOperationView<O>> extends BaseBeanPresenter<O, V> {

    @Autowired
    private ServiceEventBus serviceEventBus;

    @Override
    protected void bindBean(O bean) {
        super.bindBean(bean);
        getBinderPostCommitObservable().subscribe(commitEvent -> saveOperation());
    }

    private void saveOperation() {
        serviceEventBus.post(new SaveOperationEvent(getBean()));
    }
}
