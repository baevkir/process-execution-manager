package com.pem.core.rx.event;

import io.reactivex.Observable;


public abstract class ObservableEvent<T> extends ReactiveEvent<T> {

    public void observe(Observable<T> observable) {
        observeObservable(observable);
    }

    public Observable<T> getObservable() {
        return getEventSubject();
    }
}
