package com.pem.logic.rx.subscriber.operation;

import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.rx.subscriber.operation.event.*;
import com.pem.logic.service.operation.OperationService;
import org.springframework.util.Assert;
import rx.Observable;

import javax.annotation.PostConstruct;

public class OperationEventSubscriber {

    private ServiceEventBus eventBus;

    private OperationService operationService;

    @PostConstruct
    void init() {
        initCreateSubscriber();
        initUpdateSubscriber();
        initDeleteSubscriber();
        initGetOneSubscriber();
        initGetListSubscriber();
    }

    private void initCreateSubscriber() {
        Observable<CreateOperationEvent> observable = eventBus.getObservable(CreateOperationEvent.class);
        observable.subscribe(event -> {
            Assert.notNull(event.getSource());
            operationService.createOperation(event.getSource()).subscribe(event.getObserver());
        });
    }

    private void initUpdateSubscriber() {
        Observable<UpdateOperationEvent> observable = eventBus.getObservable(UpdateOperationEvent.class);
        observable.subscribe(event -> {
            Assert.notNull(event.getSource());
            operationService.updateOperation(event.getSource()).subscribe(event.getObserver());
        });
    }

    private void initDeleteSubscriber() {
        Observable<DeleteOperationEvent> observable = eventBus.getObservable(DeleteOperationEvent.class);
        observable.subscribe(event -> {
            Assert.notNull(event.getSource());
            operationService.deleteOperation(event.getSource()).subscribe(event.getObserver());
        });
    }

    private void initGetOneSubscriber() {
        Observable<GetOperationEvent> observable = eventBus.getObservable(GetOperationEvent.class);
        observable.subscribe(event -> {
            Assert.notNull(event.getSource());
            operationService.getOperation(event.getSource()).subscribe(event.getObserver());
        });
    }

    private void initGetListSubscriber() {
        Observable<GetOperationListEvent> observable = eventBus.getObservable(GetOperationListEvent.class);
        observable.subscribe(event -> operationService.getAllOperations().toList().subscribe(event.getObserver()));
    }

    public void setEventBus(ServiceEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void setOperationService(OperationService operationService) {
        this.operationService = operationService;
    }
}
