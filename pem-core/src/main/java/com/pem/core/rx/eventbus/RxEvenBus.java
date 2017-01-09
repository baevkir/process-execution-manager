package com.pem.core.rx.eventbus;

import com.pem.core.rx.event.BaseEvent;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RxEvenBus<E extends BaseEvent> implements EventBus<E> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RxEvenBus.class);

    private final Subject<E> eventBusSubject = PublishSubject.<E>create().toSerialized();

    @Override
    public Observable<E> getObservable() {
        LOGGER.trace("Get common Observable");
        return eventBusSubject;
    }

    @Override
    public <T extends E> Observable<T> getObservable(final Class<T> eventType) {
        LOGGER.trace("Get Observable for {}.", eventType);
        return eventBusSubject
                .filter(event -> eventType.isInstance(event))
                .map(event -> (T) event);
    }

    @Override
    public <T extends E> void post(T event) {
        LOGGER.trace("Post: {}.", event);
        eventBusSubject.onNext(event);
    }
}
