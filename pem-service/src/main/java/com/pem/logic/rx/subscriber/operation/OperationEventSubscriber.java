package com.pem.logic.rx.subscriber.operation;

import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.rx.subscriber.operation.event.*;
import com.pem.logic.service.operation.OperationService;
import io.reactivex.Observable;
import org.springframework.util.Assert;

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
            event.observe(operationService.createOperation(event.getSource()));
        });
    }

    private void initUpdateSubscriber() {
        Observable<UpdateOperationEvent> observable = eventBus.getObservable(UpdateOperationEvent.class);
        observable.subscribe(event -> {
            Assert.notNull(event.getSource());
            event.observe(operationService.updateOperation(event.getSource()));
        });
    }

    private void initDeleteSubscriber() {
        Observable<DeleteOperationEvent> observable = eventBus.getObservable(DeleteOperationEvent.class);
        observable.subscribe(event -> {
            Assert.notNull(event.getSourceId());
            event.observe(operationService.deleteOperation(event.getSourceId()));
        });
    }

    private void initGetOneSubscriber() {
        eventBus.getObservable(GetOperationEvent.class).subscribe(event -> {
            Assert.notNull(event.getSourceId());
            event.observe(operationService.getOperation(event.getSourceId()));
        });
    }

    private void initGetListSubscriber() {
        Observable<GetOperationListEvent> observable = eventBus.getObservable(GetOperationListEvent.class);
        observable.subscribe(event -> event.observe(operationService.getAllOperations()));
    }

    public void setEventBus(ServiceEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void setOperationService(OperationService operationService) {
        this.operationService = operationService;
    }
}
