package com.pem.ui.presentation.process;

import com.pem.ui.presentation.common.presenter.BasePresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class ProcessMainPresenter extends BasePresenter<ProcessMainView> {

//    @Autowired
//    private ServiceEventBus serviceEventBus;
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
