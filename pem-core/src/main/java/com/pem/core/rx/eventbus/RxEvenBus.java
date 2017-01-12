package com.pem.core.rx.eventbus;

import com.pem.core.rx.event.BaseEvent;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RxEvenBus<E extends BaseEvent> implements EventBus<E> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RxEvenBus.class);

    private Subject<E> eventBus;

    public RxEvenBus() {
        eventBus = createSubject();
    }

    @Override
    public Observable<E> getObservable() {
        LOGGER.trace("Get common Observable");
        return getEventBus()
                .doOnError(exception -> LOGGER.warn("Can't observe event.", exception))
                .doOnNext(event -> LOGGER.debug("Handle event: {}.", event))
                .doOnComplete(() -> LOGGER.warn("Event Bus completed."));
    }

    @Override
    public <T extends E> Observable<T> getObservable(final Class<T> eventType) {
        LOGGER.trace("Get Observable for {}.", eventType);
        return getObservable().ofType(eventType);
    }

    @Override
    public <T extends E> void post(T event) {
        LOGGER.trace("Post: {}.", event);
        eventBus.onNext(event);
    }

    protected Subject<E> createSubject() {
        return PublishSubject.<E>create().toSerialized();
    }

    protected Subject<E> getEventBus() {
        return eventBus;
    }
}
