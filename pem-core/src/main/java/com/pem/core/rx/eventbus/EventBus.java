package com.pem.core.rx.eventbus;

import com.pem.core.rx.event.BaseEvent;
import io.reactivex.Observable;


public interface EventBus<E extends BaseEvent> {
    Observable<E> getObservable();
    <T extends E> Observable<T> getObservable(Class<T> eventType);
    <T extends E> void post(T event);
}
