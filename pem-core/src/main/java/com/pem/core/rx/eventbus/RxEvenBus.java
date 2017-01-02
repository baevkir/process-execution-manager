package com.pem.core.rx.eventbus;

import com.pem.core.rx.event.BaseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class RxEvenBus implements EventBus<BaseEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RxEvenBus.class);

    private final Subject<BaseEvent, BaseEvent> eventBusSubject;

    public RxEvenBus() {
        PublishSubject publishSubject = PublishSubject.create();
        eventBusSubject = publishSubject.toSerialized();
    }

    @Override
    public Observable<BaseEvent> getObservable() {
        LOGGER.trace("Get common Observable");
        return eventBusSubject;
    }

    @Override
    public <T extends BaseEvent> Observable<T> getObservable(final Class<T> eventType) {
        LOGGER.trace("Get Observable for {}.", eventType);
        return eventBusSubject.filter(new Func1<BaseEvent, Boolean>() {

            @Override
            public Boolean call(BaseEvent event) {
                return eventType.isInstance(event);
            }
        }).map(new Func1<BaseEvent, T>() {
            @Override
            public T call(BaseEvent event) {
                return (T) event;
            }
        });
    }

    @Override
    public <T extends BaseEvent> void post(T event) {
        LOGGER.trace("Post: {}.", event);
        eventBusSubject.onNext(event);
    }
}
