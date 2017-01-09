package com.pem.core.rx.event;

import io.reactivex.Observable;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.springframework.util.Assert;

public abstract class ReactiveEvent<T> extends BaseEvent {

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
}
