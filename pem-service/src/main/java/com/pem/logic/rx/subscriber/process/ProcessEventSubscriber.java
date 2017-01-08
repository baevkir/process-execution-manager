package com.pem.logic.rx.subscriber.process;

import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.rx.subscriber.process.event.*;
import com.pem.logic.service.process.ExecutionProcessService;
import org.springframework.util.Assert;
import rx.Observable;

import javax.annotation.PostConstruct;

public class ProcessEventSubscriber {


    private ServiceEventBus eventBus;

    private ExecutionProcessService processService;

    @PostConstruct
    void init() {
        initCreateSubscriber();
        initUpdateSubscriber();
        initExecuteSubscriber();
        initGetOneSubscriber();
        initGetListSubscriber();
    }

    private void initCreateSubscriber() {
        Observable<CreateProcessEvent> observable = eventBus.getObservable(CreateProcessEvent.class);
        observable.subscribe(event -> {
            Assert.notNull(event.getSource());
            processService.createExecutionProcess(event.getSource()).subscribe(event.getObserver());
        });
    }

    private void initUpdateSubscriber() {
        Observable<UpdateProcessEvent> observable = eventBus.getObservable(UpdateProcessEvent.class);
        observable.subscribe(event -> {
            Assert.notNull(event.getSource());
            processService.updateExecutionProcess(event.getSource()).subscribe(event.getObserver());
        });
    }

    private void initExecuteSubscriber() {
        Observable<ExecuteProcessEvent> observable = eventBus.getObservable(ExecuteProcessEvent.class);
        observable.subscribe(event -> {
            Assert.notNull(event.getSource());
//            processService.executeProcess(event.getSource()).subscribe(event.getObserver());
        });
    }

    private void initGetOneSubscriber() {
        Observable<GetOneProcessEvent> observable = eventBus.getObservable(GetOneProcessEvent.class);
        observable.subscribe(event -> {
            Assert.notNull(event.getSource());
            processService.getExecutionProcess(event.getSource()).subscribe(event.getObserver());
        });
    }

    private void initGetListSubscriber() {
        Observable<GetProcessListEvent> observable = eventBus.getObservable(GetProcessListEvent.class);
        observable.subscribe(event -> processService.getAllExecutionProcesses().toList().subscribe(event.getObserver()));
    }

    public void setEventBus(ServiceEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void setProcessService(ExecutionProcessService processService) {
        this.processService = processService;
    }
}
