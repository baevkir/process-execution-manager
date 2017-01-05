package com.pem.logic.rx.subscriber.operation;

import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.rx.subscriber.operation.event.*;
import com.pem.logic.service.operation.OperationService;
import org.springframework.util.Assert;
import rx.Observable;
import rx.functions.Action1;

import javax.annotation.PostConstruct;

public class OperationEventSubscriber {

    private ServiceEventBus eventBus;

    private OperationService operationService;

    @PostConstruct
    void init() {
        initCreateSubscriber();
        initUpdateSubscriber();
        initDeleteSubscriber();
        initGetOneOperationSubscriber();
        initGetAllOperationsSubscriber();
    }

    private void initCreateSubscriber() {
        Observable<CreateOperationEvent> observable = eventBus.getObservable(CreateOperationEvent.class);
        observable.subscribe(new Action1<CreateOperationEvent>() {
            @Override
            public void call(CreateOperationEvent event) {
                Assert.notNull(event.getSource());
                operationService.createOperation(event.getSource())
                        .subscribe(event.getObserver());
            }
        });
    }

    private void initUpdateSubscriber() {
        Observable<UpdateOperationEvent> observable = eventBus.getObservable(UpdateOperationEvent.class);
        observable.subscribe(new Action1<UpdateOperationEvent>() {
            @Override
            public void call(UpdateOperationEvent event) {
                operationService.updateOperation(event.getSource())
                        .subscribe(event.getObserver());
            }
        });
    }

    private void initDeleteSubscriber() {
        Observable<DeleteOperationEvent> observable = eventBus.getObservable(DeleteOperationEvent.class);
        observable.subscribe(new Action1<DeleteOperationEvent>() {
            @Override
            public void call(DeleteOperationEvent event) {
                operationService.deleteOperation(event.getSourceId())
                        .subscribe(event.getObserver());
            }
        });
    }

    private void initGetOneOperationSubscriber() {
        Observable<GetOperationEvent> observable = eventBus.getObservable(GetOperationEvent.class);
        observable.subscribe(new Action1<GetOperationEvent>() {
            @Override
            public void call(GetOperationEvent event) {
                operationService.getOperation(event.getSourceId())
                        .subscribe(event.getObserver());
            }
        });
    }

    private void initGetAllOperationsSubscriber() {
        Observable<GetOperationListEvent> observable = eventBus.getObservable(GetOperationListEvent.class);
        observable.subscribe(new Action1<GetOperationListEvent>() {
            @Override
            public void call(GetOperationListEvent event) {
                operationService.getAllOperations().toList()
                        .subscribe(event.getObserver());
            }
        });
    }
    public void setEventBus(ServiceEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void setOperationService(OperationService operationService) {
        this.operationService = operationService;
    }
}
