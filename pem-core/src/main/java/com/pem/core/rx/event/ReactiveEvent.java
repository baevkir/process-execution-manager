package com.pem.core.rx.event;

import io.reactivex.Observable;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public abstract class ReactiveEvent<T> extends BaseEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveEvent.class);

    private Subject<T> eventSubject;

    public ReactiveEvent() {
        eventSubject = PublishSubject.<T>create().toSerialized();
    }

    protected void observeObservable(Observable<T> observable) {
        Assert.isTrue(!getEventSubject().hasComplete()&&!getEventSubject().hasThrowable(), "Event is already Observed");
        observable.safeSubscribe(new DefaultObserver<T>() {
            @Override
            public void onNext(T t) {
                getEventSubject().onNext(t);
            }

            @Override
            public void onError(Throwable e) {
                getEventSubject().onError(e);
            }

            @Override
            public void onComplete() {
                getEventSubject().onComplete();
            }
        });
    }

    protected  Subject<T> getEventSubject() {
        return eventSubject;
    }

    protected Observable<T> getLogedObservable() {
        return getEventSubject()
                .doOnError(exception -> LOGGER.error("Can't observe event.", exception))
                .doOnNext(bean -> LOGGER.debug("Handle bean: {} in event {}.", bean, getClass()))
                .doOnComplete(() -> LOGGER.warn("Event {} completed.", getClass()));
    }
}
