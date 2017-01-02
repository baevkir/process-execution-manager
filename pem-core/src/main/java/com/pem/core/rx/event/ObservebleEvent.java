package com.pem.core.rx.event;

import rx.Observable;

public abstract class ObservebleEvent<O> extends BaseEvent {
    Observable<O> observable;

    public ObservebleEvent(Observable<O> observable) {
        this.observable = observable;
    }

    public Observable<O> getObservable() {
        return observable;
    }
}
