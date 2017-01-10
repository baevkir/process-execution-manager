package com.pem.core.rx.event;

import io.reactivex.Completable;

public abstract class CompletableEvent extends ReactiveEvent<Void> {

    public void observe(Completable completable) {
        observeObservable(completable.toObservable());
    }

    public Completable getCompletable() {
        return getEventSubject().ignoreElements();
    }
}
