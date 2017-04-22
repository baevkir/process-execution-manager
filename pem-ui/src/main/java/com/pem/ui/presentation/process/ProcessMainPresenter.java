package com.pem.ui.presentation.process;

import com.pem.logic.service.process.ExecutionProcessService;
import com.pem.ui.presentation.common.navigator.NavigationManager;
import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.common.presenter.BasePresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

@SpringComponent
@UIScope
public class ProcessMainPresenter extends BasePresenter<ProcessMainView> {
    private ExecutionProcessService processService;
    private NavigationManager navigator;

    @Override
    public void bind(ProcessMainView view) {
        super.bind(view);
        Flux<NavigationParams> eventPublisher = navigator.onNavigationChange()
                .filter(params -> params.getViewName().equals(ProcessMainView.VIEW_NAME));

        eventPublisher.next()
                .subscribe(params -> reloadProcesses());
    }

    private void reloadProcesses() {
        getView().getProcessesList().load(processService.getAllExecutionProcesses());
    }

    @Autowired
    public void setProcessService(ExecutionProcessService processService) {
        this.processService = processService;
    }

    @Autowired
    public void setNavigator(NavigationManager navigator) {
        this.navigator = navigator;
    }

    //
//    @Override
//    protected void initViewHandlers() {
//        ProcessesList processesList = getView().getProcessesList();
//        getView().getMainViewObservable()
//                .filter(viewChangeEvent -> !processesList.isDataLoaded())
//                .subscribe(viewChangeEvent -> loadProcesses(processesList));
//
//        getView().getMainViewObservable()
//                .map(event -> event.getParameters())
//                .filter(parameters -> StringUtils.isNotEmpty(parameters) && StringUtils.isNumeric(parameters))
//                .map(parameters -> new BigInteger(parameters))
//                .subscribe(operationId -> openProcessForm(operationId));
//    }
//
//    private void loadProcesses(ProcessesList processesList) {
//        GetProcessListEvent event = new GetProcessListEvent();
//        event.getObservable().toList().subscribe(processes -> processesList.load(processes));
//        serviceEventBus.post(event);
//    }
//
//    private void openProcessForm(BigInteger operationId) {
//        GetOneProcessEvent event = new GetOneProcessEvent(operationId);
//        event.getSingle().subscribe((executionProcessDTO, throwable) -> {});
//
//        serviceEventBus.post(event);
//    }
}
