package com.pem.ui.presentation.process;

import com.google.common.eventbus.Subscribe;
import com.pem.logic.service.process.ExecutionProcessService;
import com.pem.ui.presentation.common.presenter.AbstractPresenter;
import com.pem.ui.presentation.operation.event.ShowOperationsListEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class ProcessMainPresenter extends AbstractPresenter<ProcessMainView> {

    @Autowired
    private ExecutionProcessService processService;

    @Subscribe
    public void loadAllProcesses(ShowOperationsListEvent event) {
        getView().load(processService.getAllExecutionProcesses());
    }


}
